package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.AlocacaoDTO;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    //Adicionar uma tarefa (post/tarefas)
    @PostMapping
    public ResponseEntity<Object> createTarefa(@Valid @RequestBody Tarefa tarefa) {
        try {
            Tarefa tarefaCriada = tarefaService.createTarefa(tarefa);
            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos. (get/tarefas/pendentes)
    @GetMapping("/pendentes")
    public ResponseEntity<Object> getTarefasPendentes(){
        try {
            List<Tarefa> tarefas = tarefaService.getTarefasPendentes();
            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Alocar uma pessoa na tarefa que tenha o mesmo departamento (put/tarefas/alocar/{id})
    @PutMapping("/alocar/{id}")
    public ResponseEntity<Object> alocarPessoaNaTarefa(@Valid @PathVariable Long id, @RequestBody AlocacaoDTO alocacaoDTO) {
        Long pessoaId = alocacaoDTO.getPessoaId();
        try {
            Tarefa tarefa = tarefaService.alocarPessoaNaTarefa(id, pessoaId);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Finalizar a tarefa (put/tarefas/finalizar/{id})
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Object> finalizarTarefa(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.finalizarTarefa(id);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
