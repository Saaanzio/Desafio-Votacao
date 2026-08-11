package com.rafaelsanzio.votacao.repository;

import com.rafaelsanzio.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    boolean existsByPautaIdAndDataFechamentoAfter(Long pautaId, LocalDateTime agora);
}
