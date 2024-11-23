package com.augusto.testeperinity.services;

import com.augusto.testeperinity.DTOs.PessoaMediaHorasDTO;
import com.augusto.testeperinity.DTOs.PessoaResumoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import com.augusto.testeperinity.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Pessoa createPessoa(Pessoa pessoa){
        Departamento departamento = departamentoRepository.findById(pessoa.getDepartamento().getId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        pessoa.setDepartamento(departamento);
        departamento.getPessoas().add(pessoa);

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

    public PessoaMediaHorasDTO getPessoasPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) {

        Pessoa pessoa = pessoaRepository.findPessoaByNome(nome);
        if (pessoa == null) {
            throw new RuntimeException("Nenhuma pessoa com o nome '" + nome + "' encontrada.");
        }

        List<Tarefa> tarefas = pessoa.getTarefas().stream()
                .filter(tarefa -> tarefa.getPrazo().isAfter(dataInicio.minusDays(1)) &&
                        tarefa.getPrazo().isBefore(dataFim.plusDays(1)))
                .toList();

        if (tarefas.isEmpty()) {
            throw new RuntimeException("Nenhuma tarefa encontrada para " + nome + " no período especificado.");
        }

        int totalHoras = 0;
        for (Tarefa tarefa : tarefas) {
            int duracao = tarefa.getDuracao();
            totalHoras += duracao;

        }
        double mediaHoras = (double) totalHoras / tarefas.size();

        return new PessoaMediaHorasDTO(nome, mediaHoras);
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
