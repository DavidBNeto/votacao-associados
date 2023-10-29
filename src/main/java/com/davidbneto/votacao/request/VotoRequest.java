package com.davidbneto.votacao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoRequest {

    @JsonProperty("id_pauta")
    private Long pauta;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("voto")
    private String voto;
}
