package com.rafaelsanzio.votacao.model;

import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "votos", indexes = @Index(name = "idx_voto_sessao_opcao", columnList = "sessao_id, opcao"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"sessao_id", "associado_id"})
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @Column(nullable = false)
    private String associadoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpcaoVoto opcao;

    private LocalDateTime dataVoto;

    public Voto(Sessao sessao, String associadoId, OpcaoVoto opcao, LocalDateTime dataVoto) {
        this.sessao = sessao;
        this.associadoId = associadoId;
        this.opcao = opcao;
        this.dataVoto = dataVoto;
    }
}
