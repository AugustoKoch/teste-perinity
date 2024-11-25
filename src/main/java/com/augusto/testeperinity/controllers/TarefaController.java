package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.AlocacaoDTO;
import com.augusto.testeperinity.entities.Tarefa;
import com.augusto.testeperinity.services.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gerenciamento de Tarefas")
@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    //Adicionar uma tarefa (post/tarefas)
    @Operation(summary = "Adiciona uma nova tarefa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
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
    @Operation(summary = "Lista as 3 tarefas mais antigas sem pessoa alocada", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas pendentes encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa pendente encontrada")
    })
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
    @Operation(summary = "Aloca uma pessoa na tarefa que tenha o mesmo departamento", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa alocada com sucesso na tarefa"),
            @ApiResponse(responseCode = "400",
                    description = "Pessoa ou tarefa não encontradas, ou departamentos não coincidem")
    })
    @PutMapping("/alocar/{id}")
    public ResponseEntity<Object> alocarPessoaNaTarefa(@Valid @PathVariable Long id,
                                                       @RequestBody AlocacaoDTO alocacaoDTO) {
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
    @Operation(summary = "Finaliza uma tarefa", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa finalizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Object> finalizarTarefa(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.finalizarTarefa(id);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
