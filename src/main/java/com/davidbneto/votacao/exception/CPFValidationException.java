package com.davidbneto.votacao.exception;

public class CPFValidationException extends RuntimeException {

    public CPFValidationException(String cpf) {
        super("CPF " + cpf + " inválido.");
    }

}
