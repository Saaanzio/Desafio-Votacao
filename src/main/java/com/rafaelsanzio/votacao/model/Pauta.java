package com.rafaelsanzio.votacao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pautas")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    public Pauta(String titulo, String descricao){
        this.titulo = titulo;
        this.descricao = descricao;
    }


}
