package com.davidbneto.votacao.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaCreationRequest {

    @JsonProperty("titulo")
    @Schema(requiredMode = REQUIRED, description = "Título da pauta a ser criada", example = "Festa na piscina de final de ano")
    private String titulo;

}
