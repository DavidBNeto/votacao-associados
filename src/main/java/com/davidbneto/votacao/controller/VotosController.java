package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.VotoRequest;
import com.davidbneto.votacao.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Votos", description = "Endpoint para manejo de votos")
public interface VotosController {

    @Operation(summary = "Votar em uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto feito com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"Pauta não encontrada com id 7\"}")) }),
            @ApiResponse(responseCode = "500", description = "Erro ao executar voto", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"CPF 1111111111 inválido\"}"))  })
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> votar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = VotoRequest.class), examples = @ExampleObject( value = "{\"id_pauta\": 7, \"cpf\": \"188.498.190-99\", \"voto\": \"SIM\"}", description = "Exemplo sem minutagem default")))
            @RequestBody VotoRequest votoRequest);
}
