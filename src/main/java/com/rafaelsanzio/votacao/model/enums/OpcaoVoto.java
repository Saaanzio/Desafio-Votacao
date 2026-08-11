package com.rafaelsanzio.votacao.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OpcaoVoto {
    SIM,
    NAO;

    @JsonCreator
    public static OpcaoVoto fromString(String valor){
        return OpcaoVoto.valueOf(valor.toUpperCase());
    }
}
