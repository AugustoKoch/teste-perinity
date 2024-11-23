package com.augusto.testeperinity.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @NotBlank(message = "O título deve ser preenchido.")
    private String titulo;

    @NotBlank(message = "A descrição deve ser preenchida.")
    private String descricao;

    @NotNull(message = "O prazo deve ser preenchido.")
    private LocalDate prazo;

    @NotNull(message = "A duração deve ser preenchida.")
    private Integer duracao;

    private Boolean finalizado = false;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference("pessoa-tarefa")
    private Pessoa pessoa;

    @NotNull(message = "O departamento deve ser preenchido.")
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonBackReference("departamento-tarefa")
    private Departamento departamento;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
