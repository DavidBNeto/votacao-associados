package com.davidbneto.votacao.enumeration;

import lombok.Getter;

@Getter
public enum Opcao {

    SIM("SIM"),
    NAO("NÃO");

    private final String opcao;

    private Opcao(String opcao) {
        this.opcao = opcao;
    }
}
