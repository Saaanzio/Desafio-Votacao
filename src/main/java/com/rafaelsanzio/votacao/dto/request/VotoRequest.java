package com.rafaelsanzio.votacao.dto.request;

import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;

public record VotoRequest(Long sessaoId, Long associadoId, OpcaoVoto voto) {
}
