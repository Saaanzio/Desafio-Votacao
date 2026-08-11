package com.rafaelsanzio.votacao.dto.request;

import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import jakarta.validation.constraints.NotNull;

public record VotoRequest(@NotNull(message="Id da sessão é obrigatório") Long sessaoId, @NotNull(message="Id de associado é obrigatório") String associadoId, @NotNull(message="Opção de voto é obrigatório")OpcaoVoto voto) {
}
