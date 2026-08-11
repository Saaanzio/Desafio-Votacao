package com.rafaelsanzio.votacao.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PautaRequest(@NotBlank(message = "O título é obrigatório") String titulo, String descricao) {
}
