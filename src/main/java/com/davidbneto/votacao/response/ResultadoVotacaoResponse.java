package com.davidbneto.votacao.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record ResultadoVotacaoResponse(
        @Schema(requiredMode = REQUIRED, description = "Titulo da pauta que foi votada", example = "Festa na piscina de final de ano")
        String titulo,
        @JsonProperty("votos_sim")
        @Schema(requiredMode = REQUIRED, description = "Quantidade de votos sim", example = "758648")
        Long votosSim,
        @JsonProperty("votos_nao")
        @Schema(requiredMode = REQUIRED, description = "Quantidade de votos sim", example = "3")
        Long votosNao) { }
