package com.augusto.testeperinity.repositories;

import com.augusto.testeperinity.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository  extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findTop3ByPessoaIsNullAndFinalizadoFalseOrderByPrazoAsc();
}
