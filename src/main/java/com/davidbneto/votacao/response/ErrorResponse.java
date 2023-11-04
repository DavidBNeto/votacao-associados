package com.davidbneto.votacao.response;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record ErrorResponse(
        @Schema(requiredMode = REQUIRED, description = "Erro ocorrido durante o processo", example = "Null pointer exception")
        String erro) {
}
