package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.service.VotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/voto")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> votar(@RequestBody VotoRequest votoRequest) {
        votoService.votar(votoRequest);
        return ResponseEntity.ok().build();
    }

}
