package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ResultadosController {

    @ApiOperation("Obter resultado da votação")
    @GetMapping(path = "/{id}", produces = "application/json")
    ResponseEntity<ResultadoVotacaoResponse> obterResultadoVotacao(@PathVariable Long id);
}
