package com.rafaelsanzio.votacao.dto.response;

import com.rafaelsanzio.votacao.model.Pauta;

public record PautaResponse(Long id, String titulo, String descricao) {
    public static PautaResponse fromPautaToDto(Pauta pauta){
        return new PautaResponse(pauta.getId(), pauta.getTitulo(), pauta.getDescricao());
    }
}
