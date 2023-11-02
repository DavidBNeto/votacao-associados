package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.VotoRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface VotosController {

    @ApiOperation("Votar em uma pauta")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<String> votar(@RequestBody VotoRequest votoRequest);
}
