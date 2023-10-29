package com.davidbneto.votacao.exception;

public class InvalidVoteException extends RuntimeException {

        public InvalidVoteException(String cpf, Long pauta) {
            super("CPF %s já votou na pauta %d".formatted(cpf, pauta));
        }

        public InvalidVoteException(String voto) {
            super("Voto %s inválido".formatted(voto));
        }
}
