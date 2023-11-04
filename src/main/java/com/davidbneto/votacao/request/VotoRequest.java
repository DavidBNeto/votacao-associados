package com.davidbneto.votacao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoRequest {

    @JsonProperty("id_pauta")
    @Schema(requiredMode = REQUIRED, description = "Titulo da pauta que se deseja votar", example = "7")
    private Long pauta;

    @JsonProperty("cpf")
    @Schema(requiredMode = REQUIRED, description = "CPF do associado que deseja votar", example = "188.498.190-99")
    private String cpf;

    @JsonProperty("voto")
    @Schema(requiredMode = REQUIRED, description = "Voto (aceitos: SIM, sim, não, nao, NAO, NÃO)", example = "SIM")
    private String voto;
}
