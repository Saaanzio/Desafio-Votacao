package com.rafaelsanzio.votacao.dto.request;

import jakarta.validation.constraints.NotNull;

public record SessaoRequest(@NotNull(message = "O Id de pauta é obrigatório") Long pautaId, Integer duracaoEmMinutos) {
}
