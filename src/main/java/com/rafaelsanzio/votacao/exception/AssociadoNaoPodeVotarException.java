package com.rafaelsanzio.votacao.exception;

public class AssociadoNaoPodeVotarException extends RuntimeException {
    public AssociadoNaoPodeVotarException(String message) {
        super(message);
    }
}
