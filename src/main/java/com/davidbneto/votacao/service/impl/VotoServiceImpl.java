package com.davidbneto.votacao.service.impl;


import com.davidbneto.votacao.entity.Voto;
import com.davidbneto.votacao.enumeration.Opcao;
import com.davidbneto.votacao.exception.InvalidVoteException;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.service.VotoService;
import com.davidbneto.votacao.validator.CPFValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository repository;
    private final CPFValidator cpfValidator;
    private final StringRedisTemplate cache;


    @Override
    public void votar(VotoRequest votingRequest) {

        log.info("Validando se a pauta {} existe.", votingRequest.getPauta());
        if ( isNull( cache.opsForValue().get(votingRequest.getPauta() + "") ) ) {
            log.error("Pauta com o id {} não encontrada", votingRequest.getPauta());
            throw new NoSuchElementException("Pauta não encontrada com o id: " + votingRequest.getPauta());
        }

        log.info("Validando cpf {} para votar na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());
        var cpf = votingRequest.getCpf().replaceAll("[^0-9]", ""); //cpfValidator.validarCPF(votingRequest.getCpf());
        log.info("Cpf {} válido", cpf);

        log.info("Validando se cpf {} já votou na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());

        String key = votingRequest.getPauta() + "-" + cpf;

        if ( !isNull(cache.opsForValue().get(key)) ) {
            log.error("Cpf {} já votou na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());
            throw new InvalidVoteException(votingRequest.getCpf(), votingRequest.getPauta());
        }

        votingRequest.setVoto(votingRequest.getVoto().toUpperCase(Locale.ROOT).replace("Ã", "A"));

        if ( votoNaoValido(votingRequest.getVoto().toUpperCase(Locale.ROOT)) ) {
            log.error("Voto \"{}\" inválido", votingRequest.getVoto());
            throw new InvalidVoteException(votingRequest.getVoto());
        }

        log.info("Salvando voto do cpf {} na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());

        var opcao = Opcao.valueOf(votingRequest.getVoto().toUpperCase(Locale.ROOT));
        Voto voto = new Voto(cpf, votingRequest.getPauta(), opcao);

        repository.save(voto);
        cache.opsForValue().set(key, opcao.getOpcao());
    }

    private boolean votoNaoValido(String voto) {
        return !voto.equals("SIM") && !voto.equals("NAO");
    }

}
