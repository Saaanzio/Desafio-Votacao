package com.rafaelsanzio.votacao.dto.response;

import java.time.LocalDateTime;

public record SessaoResponse(Long id, Long pautaId, LocalDateTime dataAbertura, LocalDateTime dataFechamento) {
}
