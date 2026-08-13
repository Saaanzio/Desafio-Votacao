package com.rafaelsanzio.votacao.dto.response;

import com.rafaelsanzio.votacao.model.Sessao;

import java.time.LocalDateTime;

public record SessaoResponse(Long id, Long pautaId, LocalDateTime dataAbertura, LocalDateTime dataFechamento) {
    public static SessaoResponse fromSessaoToDto(Sessao sessao){
        return new SessaoResponse(sessao.getId(), sessao.getPauta().getId(), sessao.getDataAbertura(), sessao.getDataFechamento());
    }
}
