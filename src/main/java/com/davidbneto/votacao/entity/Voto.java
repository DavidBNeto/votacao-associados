package com.davidbneto.votacao.entity;

import com.davidbneto.votacao.enumeration.Opcao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@Table(name = "VOTO")
@NoArgsConstructor
public class Voto {

    public Voto(String cpf, Long pauta, Opcao voto) {
        this.cpf = cpf;
        this.pauta = pauta;
        this.voto = voto;
        this.hora = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "VOTO_GEN")
    @SequenceGenerator(name = "VOTO_GEN", sequenceName = "VOTO_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "ID_PAUTA")
    private Long pauta;
    @Column(name = "VOTO")
    @Enumerated(EnumType.STRING)
    private Opcao voto;
    @Column(name = "HORA")
    private LocalDateTime hora;

}
