package com.rafaelsanzio.votacao.exception;

public class SessaoFechadaException extends RuntimeException{

    public SessaoFechadaException(String mensagem){
        super(mensagem);
    }
}
