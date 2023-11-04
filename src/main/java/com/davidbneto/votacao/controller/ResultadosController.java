package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.response.ErrorResponse;
import com.davidbneto.votacao.response.PautaCreationResponse;
import com.davidbneto.votacao.response.ResultadoVotacaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Resultados", description = "Endpoint para manejo de resultados")
public interface ResultadosController {

    @Operation(summary = "Obter resultado da votação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado obtido com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PautaCreationResponse.class), examples = @ExampleObject(value = "{\"titulo\": \"Festa na piscina de final de ano\", \"votos_sim\": 758648, \"votos_nao\": 3}")) }),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"Pauta não encontrada com id 7\"}")) }),
            @ApiResponse(responseCode = "500", description = "Erro ao obter resultado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"A votação da pauta 7 ainda não foi iniciada\"}"))  })
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    ResponseEntity<ResultadoVotacaoResponse> obterResultadoVotacao(@Parameter(description = "Id da pauta cujo resultado se deseja ser obtido", required = true) @PathVariable Long id);
}
