package com.rafaelsanzio.votacao.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SessaoRequest(@NotNull(message = "O Id de pauta é obrigatório") Long pautaId, @Positive Integer duracaoEmMinutos) {
}
