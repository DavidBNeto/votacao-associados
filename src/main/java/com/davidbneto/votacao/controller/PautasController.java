package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.request.PautaCreationRequest;
import com.davidbneto.votacao.request.PautaVotingRequest;
import com.davidbneto.votacao.response.ErrorResponse;
import com.davidbneto.votacao.response.PautaCreationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pautas", description = "Endpoint para manejo de pautas")
public interface PautasController {

    @Operation(summary = "Criar Pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PautaCreationResponse.class), examples = @ExampleObject(value = "{\"id\": 1}")) }),
            @ApiResponse(responseCode = "500", description = "Erro ao criar pauta", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"Null pointer exception\"}"))  })
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<PautaCreationResponse> criarPauta(@Parameter(required = true) @RequestBody PautaCreationRequest pautaCreationRequest);

    @Operation(summary = "Iniciar votação de pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Votação iniciada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"Pauta não encontrada com id 7\"}")) }),
            @ApiResponse(responseCode = "500", description = "Erro ao iniciar votação", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(value = "{\"erro\": \"Votação já iniciada da pauta com id 7\"}")) })
    })
    @PostMapping(path = "/iniciar", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> iniciarVotacao(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaVotingRequest.class), examples = @ExampleObject( value = "{\"minutos\": 20, \"id_pauta\": 7}", description = "Exemplo sem minutagem default")))
            @RequestBody PautaVotingRequest votingRequest);
}
