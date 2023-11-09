package com.davidbneto.votacao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class PautaVotingRequest {

    @JsonProperty("minutos")
    @Schema(description = "Tempo em minutos que a pauta deve ficar aberta (default = 1min)", requiredMode = NOT_REQUIRED)
    private Integer minutos;

    @NotNull
    @JsonProperty("id_pauta")
    @Schema(description = "ID da pauta para ser votada", requiredMode = REQUIRED)
    private final Long id;

}
