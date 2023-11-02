package com.davidbneto.votacao.controller.impl;

import com.davidbneto.votacao.controller.ResultadosController;
import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import com.davidbneto.votacao.service.ResultadoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/resultados")
public class ResultadosControllerImpl implements ResultadosController {

    private final ResultadoService resultadoService;

    @Override
    public ResponseEntity<ResultadoVotacaoResponse> obterResultadoVotacao(@PathVariable Long id) {
        ResultadoVotacaoResponse resultado = resultadoService.obterResultadoVotacao(id);

        return ResponseEntity.ok().body(resultado);
    }

}
