package com.rafaelsanzio.votacao.dto.response;

import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;

import java.time.LocalDateTime;

public record VotoResponse(Long id, Long sessaoId, Long associadoId, OpcaoVoto voto, LocalDateTime dataVoto) {
    public static VotoResponse fromVotoToDto(Voto voto){
        return new VotoResponse(voto.getId(), voto.getSessao().getId(), voto.getAssociadoId(), voto.getOpcao(), voto.getDataVoto());
    }
}
