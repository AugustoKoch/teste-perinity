package com.augusto.testeperinity.services;

import com.augusto.testeperinity.DTOs.DepartamentoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @InjectMocks
    private DepartamentoService departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Test
    @DisplayName("Deve salvar um novo departamento com sucesso")
    void createDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setNome("Desenvolvimento");

        Mockito.when(departamentoRepository.save(Mockito.any(Departamento.class)))
                .thenReturn(departamento);

        Departamento resultado = departamentoService.createDepartamento(departamento);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Desenvolvimento", resultado.getNome());
        Mockito.verify(departamentoRepository).save(departamento);
    }

    @Test
    @DisplayName("Deve retornar lista de departamentos com dados em DTO")
    void getDepartamentosCase1() {
        Departamento departamento1 = new Departamento();
        departamento1.setId(1L);
        departamento1.setNome("Financeiro");
        departamento1.setPessoas(List.of(new Pessoa(), new Pessoa()));
        departamento1.setTarefas(List.of(new Tarefa(), new Tarefa(), new Tarefa()));

        Departamento departamento2 = new Departamento();
        departamento2.setId(2L);
        departamento2.setNome("Comercial");
        departamento2.setPessoas(List.of(new Pessoa()));
        departamento2.setTarefas(List.of(new Tarefa()));

        Mockito.when(departamentoRepository.findAll()).thenReturn(List.of(departamento1, departamento2));

        List<DepartamentoDTO> resultado = departamentoService.getDepartamentos();

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());

        DepartamentoDTO dto1 = resultado.get(0);
        Assertions.assertEquals("Financeiro", dto1.getNome());
        Assertions.assertEquals(2, dto1.getQuantidadePessoas());
        Assertions.assertEquals(3, dto1.getQuantidadeTarefas());

        DepartamentoDTO dto2 = resultado.get(1);
        Assertions.assertEquals("Comercial", dto2.getNome());
        Assertions.assertEquals(1, dto2.getQuantidadePessoas());
        Assertions.assertEquals(1, dto2.getQuantidadeTarefas());

        Mockito.verify(departamentoRepository).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver departamentos cadastrados")
    void getDepartamentosCase2() {
        Mockito.when(departamentoRepository.findAll()).thenReturn(Collections.emptyList());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            departamentoService.getDepartamentos();
        });

        Assertions.assertEquals("Nenhum departamento cadastrado", exception.getMessage());
        Mockito.verify(departamentoRepository).findAll();
    }
}