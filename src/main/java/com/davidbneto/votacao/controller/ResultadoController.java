package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import com.davidbneto.votacao.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/resultado")
public class ResultadoController {

    private final ResultadoService resultadoService;

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ResultadoVotacaoResponse> obterResultadoVotacao(@PathVariable Long id) {
        ResultadoVotacaoResponse resultado = resultadoService.obterResultadoVotacao(id);

        return ResponseEntity.ok().body(resultado);
    }

}
