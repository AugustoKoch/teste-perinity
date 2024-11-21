package com.augusto.testeperinity.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate prazo;

    private Duration duracao;

    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference("pessoa-tarefa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonBackReference("departamento-tarefa")
    private Departamento departamento;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
