package com.rafaelsanzio.votacao.repository;

import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByPautaIdAndAssociadoId(Long sessaoId, String associadoId);
    Long countBySessaoIdAndOpcao(Long sessaoId, OpcaoVoto opcao);
}
