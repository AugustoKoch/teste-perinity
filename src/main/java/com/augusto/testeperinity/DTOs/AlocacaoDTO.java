package com.augusto.testeperinity.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlocacaoDTO {

    @NotNull(message = "O ID da pessoa deve ser preenchido")
    private Long pessoaId;
}
