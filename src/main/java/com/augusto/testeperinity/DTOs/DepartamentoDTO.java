package com.augusto.testeperinity.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepartamentoDTO {

    private String nome;
    private int quantidadePessoas;
    private int quantidadeTarefas;
}
