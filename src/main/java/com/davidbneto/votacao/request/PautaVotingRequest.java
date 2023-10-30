package com.davidbneto.votacao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class PautaVotingRequest {

    @JsonProperty("minutos")
    private Integer minutos;
    @JsonProperty("id_pauta")
    private final Long id;

}
