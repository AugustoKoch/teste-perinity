package com.augusto.testeperinity.services;

import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import com.augusto.testeperinity.repositories.PessoaRepository;
import com.augusto.testeperinity.repositories.TarefaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Test
    @DisplayName("Deve salvar uma nova tarefa com sucesso")
    void createTarefaCase1() {
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setTarefas(new ArrayList<>());

        Tarefa tarefa = new Tarefa();
        tarefa.setDepartamento(departamento);

        Mockito.when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa tarefaCriada = tarefaService.createTarefa(tarefa);

        Assertions.assertEquals(departamento, tarefaCriada.getDepartamento());
        Mockito.verify(departamentoRepository).findById(1L);
        Mockito.verify(tarefaRepository).save(tarefa);
    }

    @Test
    @DisplayName("Deve lançar exceção se departamento não existir ao criar tarefa")
    void createTarefaCase2() {
        Tarefa tarefa = new Tarefa();
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        tarefa.setDepartamento(departamento);

        Mockito.when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> tarefaService.createTarefa(tarefa));
        Assertions.assertEquals("Departamento não encontrado", exception.getMessage());

        Mockito.verify(departamentoRepository).findById(1L);
        Mockito.verifyNoInteractions(tarefaRepository);
    }

    @Test
    @DisplayName("Deve retornar lista de tarefas pendentes")
    void getTarefasPendentesCase1() {
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setFinalizado(false);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setFinalizado(false);

        Mockito.when(tarefaRepository.findTop3ByPessoaIsNullAndFinalizadoFalseOrderByPrazoAsc())
                .thenReturn(List.of(tarefa1, tarefa2));

        List<Tarefa> tarefasPendentes = tarefaService.getTarefasPendentes();

        Assertions.assertEquals(2, tarefasPendentes.size());
        Assertions.assertEquals("Tarefa 1", tarefasPendentes.get(0).getTitulo());
        Assertions.assertEquals("Tarefa 2", tarefasPendentes.get(1).getTitulo());

        Mockito.verify(tarefaRepository).findTop3ByPessoaIsNullAndFinalizadoFalseOrderByPrazoAsc();
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhuma tarefa pendente for encontrada")
    void getTarefasPendentesCase2() {
        Mockito.when(tarefaRepository.findTop3ByPessoaIsNullAndFinalizadoFalseOrderByPrazoAsc())
                .thenReturn(Collections.emptyList());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            tarefaService.getTarefasPendentes();
        });

        Assertions.assertEquals("Nenhuma tarefa pendente encontrada", exception.getMessage());
        Mockito.verify(tarefaRepository).findTop3ByPessoaIsNullAndFinalizadoFalseOrderByPrazoAsc();
    }


    @Test
    @DisplayName("Deve associar pessoa à tarefa com sucesso")
    void alocarPessoaNaTarefaCase1() {
        Departamento departamento = new Departamento();
        departamento.setId(1L);

        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDepartamento(departamento);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setDepartamento(departamento);
        pessoa.setTarefas(new ArrayList<>());

        Mockito.when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa tarefaAtualizada = tarefaService.alocarPessoaNaTarefa(1L, 1L);

        Assertions.assertEquals(pessoa, tarefaAtualizada.getPessoa());
        Mockito.verify(tarefaRepository).findById(1L);
        Mockito.verify(pessoaRepository).findById(1L);
        Mockito.verify(tarefaRepository).save(tarefa);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a tarefa não for encontrada")
    void alocarPessoaNaTarefaCase2() {
        Long tarefaId = 1L;
        Long pessoaId = 1L;

        Mockito.when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId));

        Assertions.assertEquals("Tarefa não encontrada", exception.getMessage());
        Mockito.verify(tarefaRepository).findById(tarefaId);
        Mockito.verifyNoInteractions(pessoaRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a pessoa não for encontrada")
    void alocarPessoaNaTarefaCase3() {
        Long tarefaId = 1L;
        Long pessoaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        Mockito.when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId));

        Assertions.assertEquals("Pessoa não encontrada", exception.getMessage());
        Mockito.verify(tarefaRepository).findById(tarefaId);
        Mockito.verify(pessoaRepository).findById(pessoaId);
        Mockito.verifyNoMoreInteractions(tarefaRepository);
    }


    @Test
    @DisplayName("Deve lançar exceção se os departamentos da pessoa e da tarefa não coincidirem")
    void alocarPessoaNaTarefaCase4() {
        Departamento departamento1 = new Departamento();
        departamento1.setId(1L);

        Departamento departamento2 = new Departamento();
        departamento2.setId(2L);

        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDepartamento(departamento1);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setDepartamento(departamento2);

        Mockito.when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> tarefaService.alocarPessoaNaTarefa(1L, 1L));
        Assertions.assertEquals("Os departamentos de pessoa e tarefa não coincidem.", exception.getMessage());

        Mockito.verify(tarefaRepository).findById(1L);
        Mockito.verify(pessoaRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    @DisplayName("Deve marcar tarefa como finalizada")
    void finalizarTarefaCase1() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setFinalizado(false);

        Mockito.when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa tarefaFinalizada = tarefaService.finalizarTarefa(1L);

        Assertions.assertTrue(tarefaFinalizada.getFinalizado());
        Mockito.verify(tarefaRepository).findById(1L);
        Mockito.verify(tarefaRepository).save(tarefa);
    }

    @Test
    @DisplayName("Deve lançar exceção se a tarefa não existir ao finalizar")
    void finalizarTarefaCase2() {
        Mockito.when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> tarefaService.finalizarTarefa(1L));
        Assertions.assertEquals("Tarefa não encontrada", exception.getMessage());

        Mockito.verify(tarefaRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(tarefaRepository);
    }
}