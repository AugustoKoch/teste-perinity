package com.augusto.testeperinity.services;


import com.augusto.testeperinity.DTOs.PessoaMediaHorasDTO;
import com.augusto.testeperinity.DTOs.PessoaResumoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import com.augusto.testeperinity.repositories.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    @DisplayName("Deve salvar uma nova pessoa com sucesso")
    void createPessoaCase1() {
        Departamento departamentoMock = new Departamento();
        departamentoMock.setId(1L);
        departamentoMock.setNome("Financeiro");
        departamentoMock.setPessoas(new ArrayList<>());

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Augusto");
        pessoa.setDepartamento(departamentoMock);

        Pessoa pessoaSalva = new Pessoa();
        pessoaSalva.setId(1L);
        pessoaSalva.setNome("Augusto");
        pessoaSalva.setDepartamento(departamentoMock);

        Mockito.when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamentoMock));
        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoaSalva);

        Pessoa resultado = pessoaService.createPessoa(pessoa);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Augusto", resultado.getNome());
        Assertions.assertEquals("Financeiro", resultado.getDepartamento().getNome());

        Mockito.verify(departamentoRepository).findById(1L);
        Mockito.verify(pessoaRepository).save(Mockito.any(Pessoa.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se departamento não existir ao criar pessoa")
    void createPessoaCase2() {
        Pessoa pessoa = new Pessoa();
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        pessoa.setDepartamento(departamento);

        Mockito.when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.createPessoa(pessoa);
        });

        Assertions.assertEquals("Departamento não encontrado", exception.getMessage());
        Mockito.verify(departamentoRepository).findById(1L);
        Mockito.verifyNoInteractions(pessoaRepository);
    }

    @Test
    @DisplayName("Deve retornar uma lista de pessoas com as horas totais")
    void getPessoasTotalHorasCase1() {
        Departamento departamento = new Departamento();
        departamento.setNome("Desenvolvimento");

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setDuracao(5);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setDuracao(3);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Augusto");
        pessoa.setDepartamento(departamento);
        pessoa.setTarefas(List.of(tarefa1, tarefa2));

        Mockito.when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<PessoaResumoDTO> resultado = pessoaService.getPessoasTotalHoras();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Augusto", resultado.get(0).getNome());
        Assertions.assertEquals("Desenvolvimento", resultado.get(0).getDepartamento());
        Assertions.assertEquals(8, resultado.get(0).getTotalHorasGastas());

        Mockito.verify(pessoaRepository).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhuma pessoa estiver cadastrada")
    void getPessoasTotalHorasCase2() {
        Mockito.when(pessoaRepository.findAll()).thenReturn(Collections.emptyList());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.getPessoasTotalHoras();
        });

        Assertions.assertEquals("Nenhuma pessoa cadastrada", exception.getMessage());
        Mockito.verify(pessoaRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar a média de horas trabalhadas por pessoa em um período")
    void getPessoaHorasPorPeriodoCase1() {
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setPrazo(LocalDate.of(2024, 1, 1));
        tarefa1.setDuracao(4);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setPrazo(LocalDate.of(2024, 1, 5));
        tarefa2.setDuracao(6);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Augusto");
        pessoa.setTarefas(List.of(tarefa1, tarefa2));

        Mockito.when(pessoaRepository.findPessoaByNome("Augusto")).thenReturn(pessoa);

        PessoaMediaHorasDTO resultado = pessoaService.getPessoaHorasPorPeriodo(
                "Augusto", LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5)
        );

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Augusto", resultado.getNome());
        Assertions.assertEquals(5.0, resultado.getMediaHorasGastas());

        Mockito.verify(pessoaRepository).findPessoaByNome("Augusto");
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhuma pessoa com o nome especificado for encontrada")
    void getPessoaHorasPorPeriodoCase2() {
        String nome = "Augusto";
        Mockito.when(pessoaRepository.findPessoaByNome(nome)).thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.getPessoaHorasPorPeriodo(nome, LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 1, 31));
        });

        Assertions.assertEquals("Nenhuma pessoa com o nome 'Augusto' encontrada.", exception.getMessage());
        Mockito.verify(pessoaRepository).findPessoaByNome(nome);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhuma tarefa for encontrada no período especificado")
    void getPessoaHorasPorPeriodoCase3() {
        String nome = "Augusto";
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setTarefas(Collections.emptyList());

        Mockito.when(pessoaRepository.findPessoaByNome(nome)).thenReturn(pessoa);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.getPessoaHorasPorPeriodo(nome, LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 1, 31));
        });

        Assertions.assertEquals("Nenhuma tarefa encontrada para Augusto no período especificado.",
                exception.getMessage());
        Mockito.verify(pessoaRepository).findPessoaByNome(nome);
    }


    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso")
    void updatePessoaCase1() {
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(1L);
        pessoaExistente.setNome("Augusto");

        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Augusto Atualizado");

        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaExistente));
        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoaExistente);

        Pessoa resultado = pessoaService.updatePessoa(1L, pessoaAtualizada);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Augusto Atualizado", resultado.getNome());

        Mockito.verify(pessoaRepository).findById(1L);
        Mockito.verify(pessoaRepository).save(Mockito.any(Pessoa.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se a pessoa não existir ao atualizar")
    void updatePessoaCase2() {
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Augusto");

        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.updatePessoa(1L, pessoaAtualizada);
        });

        Assertions.assertEquals("Pessoa não encontrada", exception.getMessage());
        Mockito.verify(pessoaRepository).findById(1L);
        Mockito.verifyNoInteractions(departamentoRepository);
    }

    @Test
    @DisplayName("Deve deletar uma pessoa com sucesso")
    void deletePessoaCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        pessoaService.deletePessoa(1L);

        Mockito.verify(pessoaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção se a pessoa não existir ao deletar")
    void deletePessoaCase2() {
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pessoaService.deletePessoa(1L);
        });

        Assertions.assertEquals("Pessoa não encontrada", exception.getMessage());
        Mockito.verify(pessoaRepository).findById(1L);
    }
}