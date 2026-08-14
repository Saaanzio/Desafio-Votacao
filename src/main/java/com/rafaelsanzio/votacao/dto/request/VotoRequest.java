package com.rafaelsanzio.votacao.dto.request;

import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VotoRequest(@NotNull(message="Id da sessão é obrigatório") @Positive(message = "Sessão Id precisa ser um valor positivo") Long sessaoId, @NotBlank(message="Id de associado é obrigatório") @Size(max = 14, message = "O associadoId deve ter no máximo 14 caracteres") String associadoId, @NotNull(message="Opção de voto é obrigatório")OpcaoVoto voto) {
}
