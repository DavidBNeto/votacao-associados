package com.davidbneto.votacao.service;

import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import org.springframework.stereotype.Service;

@Service
public interface ResultadoService {

    ResultadoVotacaoResponse obterResultadoVotacao(Long pautaId);

}
