package com.davidbneto.votacao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PautaVotingRequest {

    private int minutos;
    private final long id;

}
