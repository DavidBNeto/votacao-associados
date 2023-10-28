package com.davidbneto.votacao.controller;


import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Controller
@RequestMapping("/v1/pauta")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PautaCreationResponse> criarPauta(@RequestBody PautaCreationRequest pautaCreationRequest) {
        var response = pautaService.criarPauta(pautaCreationRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping(path = "/iniciar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> iniciarVotacao(@RequestBody PautaVotingRequest votingRequest) {
        pautaService.iniciarVotacao(votingRequest);
        return ResponseEntity.ok().build();
    }
}
