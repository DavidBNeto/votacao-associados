package com.davidbneto.votacao.response;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record PautaCreationResponse(
        @Schema(requiredMode = REQUIRED, description = "ID da pauta criada", example = "7")
        Long id) { }
