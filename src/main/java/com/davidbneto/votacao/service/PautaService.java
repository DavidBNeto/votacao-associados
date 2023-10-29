package com.davidbneto.votacao.service;

import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import org.springframework.stereotype.Service;

@Service
public interface PautaService {

    PautaCreationResponse criarPauta(PautaCreationRequest pautaCreationRequest);
    void iniciarVotacao(PautaVotingRequest pautaVotingRequest);

}
