package com.davidbneto.votacao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PautaVotingRequest {

    @JsonProperty("minutos")
    private int minutos;
    @JsonProperty("id_pauta")
    private final long id;

}
