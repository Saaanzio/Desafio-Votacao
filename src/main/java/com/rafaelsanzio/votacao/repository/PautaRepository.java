package com.rafaelsanzio.votacao.repository;

import com.rafaelsanzio.votacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
