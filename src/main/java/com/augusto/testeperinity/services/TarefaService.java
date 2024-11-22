package com.augusto.testeperinity.services;

import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import com.augusto.testeperinity.repositories.PessoaRepository;
import com.augusto.testeperinity.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;


    public Tarefa createTarefa(Tarefa tarefa) {

        Departamento departamento = departamentoRepository.findById(tarefa.getDepartamento().getId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        tarefa.setDepartamento(departamento);
        departamento.getTarefas().add(tarefa);

        return tarefaRepository.save(tarefa);
    }

    public Tarefa alocarPessoa(Long tarefaId, Long pessoaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não econtrada"));

        if (!tarefa.getDepartamento().equals(pessoa.getDepartamento())) {
            throw new RuntimeException("Os departamentos de pessoa e tarefa não coincidem.");
        }

        tarefa.setPessoa(pessoa);
        pessoa.getTarefas().add(tarefa);

        return tarefaRepository.save(tarefa);
    }
}
