package com.augusto.testeperinity.services;

import com.augusto.testeperinity.DTOs.PessoaResumoDTO;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa createPessoa(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    private static int somaTotalHoras(Pessoa pessoa) {
        return pessoa.getTarefas().stream()
                .mapToInt(tarefa -> tarefa.getDuracao() != null ? tarefa.getDuracao() : 0)
                .sum();
    }

    public List<PessoaResumoDTO> getPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas.stream()
                .map(pessoa -> new PessoaResumoDTO(
                        pessoa.getNome(),
                        pessoa.getDepartamento() != null ? pessoa.getDepartamento().getNome() : "Sem departamento", //remover esse ternario quando ajustar resgistros
                        somaTotalHoras(pessoa)
                ))
                .toList();
    }

    public Pessoa updatePessoa(Long id, Pessoa pessoa){
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isPresent()){
            Pessoa pessoaExistente = pessoaOptional.get();

            BeanUtils.copyProperties(pessoa, pessoaExistente, "id","tarefas" );


            pessoaExistente.getTarefas().clear();
            if (pessoa.getTarefas() != null) {
                for (Tarefa tarefa : pessoa.getTarefas()) {
                    tarefa.setPessoa(pessoaExistente);
                    pessoaExistente.getTarefas().add(tarefa);
                }
            }
            return pessoaRepository.save(pessoaExistente);

        } else {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    public void deletePessoa(Long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isPresent()){
            pessoaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }
}
