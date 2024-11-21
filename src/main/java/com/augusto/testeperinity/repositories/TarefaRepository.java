package com.augusto.testeperinity.repositories;

import com.augusto.testeperinity.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository  extends JpaRepository<Tarefa, Long> {
}
