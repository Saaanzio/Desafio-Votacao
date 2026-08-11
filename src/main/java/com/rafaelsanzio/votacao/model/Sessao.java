package com.rafaelsanzio.votacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="pauta_id", nullable = false)
    private Pauta pauta;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    public Sessao(Pauta pauta, LocalDateTime dataAbertura, LocalDateTime dataFechamento) {
        this.pauta = pauta;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
    }
}
