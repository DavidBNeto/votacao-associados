package com.davidbneto.votacao.service.impl;

import com.davidbneto.votacao.entity.Pauta;
import com.davidbneto.votacao.exception.PautaException;
import com.davidbneto.votacao.repository.PautaRepository;
import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;

    @Override
    public PautaCreationResponse criarPauta(PautaCreationRequest pautaCreationRequest) {
        var pauta = pautaRepository.save(new Pauta(pautaCreationRequest.getTitulo()));
        log.info("Pauta {} criada com sucesso", pauta.getId());
        return new PautaCreationResponse(pauta.getId());
    }

    @Override
    public void iniciarVotacao(PautaVotingRequest pautaVotingRequest) {
        var possiblePauta = pautaRepository.findById(pautaVotingRequest.getId());

        if (possiblePauta.isEmpty()) {
            log.error("Pauta {} não encontrada", pautaVotingRequest.getId());
            throw new PautaException("Pauta não encontrada");
        }

        var pauta = possiblePauta.get();

        if (!isNull(pauta.getInicioDaVotacao())) {
            log.error("Pauta {} já iniciada", pauta.getId());
            throw new PautaException("Pauta já iniciada");
        }

        pauta.setInicioDaVotacao(LocalDateTime.now());
        pauta.setFimDaVotacao(pauta.getInicioDaVotacao().plusMinutes(pautaVotingRequest.getMinutos()));
        pautaRepository.save(pauta);
        log.info("Votação da pauta {} iniciada com sucesso", pauta.getId());
    }
}
