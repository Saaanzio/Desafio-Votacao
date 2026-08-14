package com.rafaelsanzio.votacao.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SessaoRequest(@NotNull(message = "O Id de pauta é obrigatório") @Positive(message = "Pauta Id precisa ser um valor positivo") Long pautaId, @Positive(message = "A duração em minutos precisa ser um valor positivo") @Max(value = 360, message = "Duração máxima é de 360 minutos") Integer duracaoEmMinutos) {
}
