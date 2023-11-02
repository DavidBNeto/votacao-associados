package com.davidbneto.votacao.controller.impl;

import com.davidbneto.votacao.controller.PautasController;
import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.service.PautaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pautas")
public class PautasControllerImpl implements PautasController {

    private final PautaService pautaService;

    @Override
    public ResponseEntity<PautaCreationResponse> criarPauta(@RequestBody PautaCreationRequest pautaCreationRequest) {
        var response = pautaService.criarPauta(pautaCreationRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<String> iniciarVotacao(@RequestBody PautaVotingRequest votingRequest) {
        pautaService.iniciarVotacao(votingRequest);
        return ResponseEntity.ok().build();
    }
}
