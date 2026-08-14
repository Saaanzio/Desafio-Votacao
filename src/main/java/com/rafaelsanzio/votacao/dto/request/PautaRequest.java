package com.rafaelsanzio.votacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PautaRequest(@NotBlank(message = "O título é obrigatório") @Size(max = 50, message = "Título deve ter no máximo 50 caracteres") String titulo, @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres") String descricao) {
}
