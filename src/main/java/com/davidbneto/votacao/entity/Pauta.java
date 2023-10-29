package com.davidbneto.votacao.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@Table(name = "PAUTA")
@NoArgsConstructor
public class Pauta {

    public Pauta(String titulo) {
        this.titulo = titulo;
    }

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "PAUTA_GEN")
    @SequenceGenerator(name = "PAUTA_GEN", sequenceName = "PAUTA_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "VOTOS_SIM", nullable = true)
    private Long votosSim;
    @Column(name = "VOTOS_NAO", nullable = true)
    private Long votosNao;
    @Column(name = "INICIO_VOTACAO", nullable = true)
    private LocalDateTime inicioDaVotacao;
    @Column(name = "FIM_VOTACAO", nullable = true)
    private LocalDateTime fimDaVotacao;


}
