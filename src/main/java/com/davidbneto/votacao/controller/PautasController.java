package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PautasController {

    @ApiOperation("Criar Pauta")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<PautaCreationResponse> criarPauta(@RequestBody PautaCreationRequest pautaCreationRequest);

    @ApiOperation("Iniciar votação de pauta")
    @PostMapping(path = "/iniciar", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> iniciarVotacao(@RequestBody PautaVotingRequest votingRequest);
}
