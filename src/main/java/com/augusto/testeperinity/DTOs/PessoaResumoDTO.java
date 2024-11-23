package com.augusto.testeperinity.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PessoaResumoDTO {

    private String nome;
    private String departamento;
    private int totalHorasGastas;
}
