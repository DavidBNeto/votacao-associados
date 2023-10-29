package com.davidbneto.votacao.service.impl;


import com.davidbneto.votacao.entity.Voto;
import com.davidbneto.votacao.enumeration.Opcao;
import com.davidbneto.votacao.exception.InvalidVoteException;
import com.davidbneto.votacao.repository.VotoRepository;
import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.service.VotoService;
import com.davidbneto.votacao.validator.CPFValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository repository;
    private final CPFValidator cpfValidator;

    @Override
    public void votar(VotoRequest votingRequest) {

        log.info("Validando cpf {} para votar na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());
        var cpf = cpfValidator.validarCPF(votingRequest.getCpf());
        log.info("Cpf {} válido", cpf);


        if (votoNaoValido(votingRequest.getVoto().toUpperCase(Locale.ROOT))) {
            throw new InvalidVoteException(votingRequest.getVoto());
        }

        log.info("Validando se cpf {} já votou na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());

        repository.findByCpf(votingRequest.getCpf())
                .ifPresent(voto -> {
                    throw new InvalidVoteException(votingRequest.getCpf(), votingRequest.getPauta());
                });

        log.info("Salvando voto do cpf {} na pauta {}", votingRequest.getCpf(), votingRequest.getPauta());

        var opcao = Opcao.valueOf(votingRequest.getVoto().toUpperCase(Locale.ROOT));
        Voto voto = new Voto(cpf, votingRequest.getPauta(), opcao);
        repository.save(voto);
    }

    private boolean votoNaoValido(String voto) {
        return !voto.equals("SIM") && !voto.equals("NAO");
    }

}
