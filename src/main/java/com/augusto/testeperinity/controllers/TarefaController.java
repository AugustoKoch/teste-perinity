package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.AlocacaoDTO;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> createTarefa(@Valid @RequestBody Tarefa tarefa) {
            Tarefa tarefaCriada = tarefaService.createTarefa(tarefa);
            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
    }

    @PutMapping("/alocar/{id}")
    public ResponseEntity<Object> alocarPessoa(@PathVariable Long id, @RequestBody AlocacaoDTO alocacaoDTO) {
        Long pessoaId = alocacaoDTO.getPessoaId();
        try {
            Tarefa tarefa = tarefaService.alocarPessoa(id, pessoaId);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
