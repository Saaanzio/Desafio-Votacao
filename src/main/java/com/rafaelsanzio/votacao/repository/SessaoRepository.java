package com.rafaelsanzio.votacao.repository;

import com.rafaelsanzio.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
}
