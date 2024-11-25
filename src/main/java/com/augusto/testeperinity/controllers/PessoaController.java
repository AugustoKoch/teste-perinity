package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.PessoaMediaHorasDTO;
import com.augusto.testeperinity.DTOs.PessoaResumoDTO;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Gerenciamento de Pessoas")
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;


    //Adicionar uma pessoa (post/pessoas)
    @Operation(summary = "Adiciona uma nova pessoa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PostMapping
    public ResponseEntity<Object> createPessoa(@Valid @RequestBody Pessoa pessoa){

        try {
            Pessoa pessoaCriada = pessoaService.createPessoa(pessoa);
            return new ResponseEntity<>(pessoaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Listar pessoas trazendo nome, departamento, total de horas gastas nas tarefas.(get/pessoas)
    @Operation(summary = "Lista todas as pessoas com o total de horas gastas em tarefas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoas encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nenhuma pessoa encontrada")
    })
    @GetMapping
    public ResponseEntity<Object> getPessoasTotalHoras() {
        try {
            List<PessoaResumoDTO> pessoas = pessoaService.getPessoasTotalHoras();
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Buscar pessoas por nome e período, retorna média de horas gastas por tarefa. (get/pessoas/gastos)
    @Operation(summary = "Busca média de horas gastas por tarefa de uma pessoa em um período", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média de horas calculadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa ou tarefas para o periodo não encontradas")
    })
    @GetMapping("/gastos")
    public ResponseEntity<Object> getPessoaHorasPorPeriodo(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        try {
            PessoaMediaHorasDTO pessoaMediaHorasDTO = pessoaService.getPessoaHorasPorPeriodo(nome, dataInicio, dataFim);
            return new ResponseEntity<>(pessoaMediaHorasDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Alterar uma pessoa (put/pessoas/{id})
    @Operation(summary = "Atualiza uma pessoa pelo ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable Long id,@Valid @RequestBody Pessoa pessoa){
        try {
            Pessoa pessoaAlterada = pessoaService.updatePessoa(id, pessoa);
            return new ResponseEntity<>(pessoaAlterada, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //Remover pessoa (delete/pessoas/{id})
    @Operation(summary = "Remove uma pessoa pelo ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable Long id){
        try {
            pessoaService.deletePessoa(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}