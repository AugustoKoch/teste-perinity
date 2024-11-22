package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Object> createTarefa(@Valid @RequestBody Tarefa tarefa) {

            Tarefa tarefaCriada = tarefaService.createTarefa(tarefa);
            return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
    }
}
