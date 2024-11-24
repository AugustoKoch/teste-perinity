package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.PessoaMediaHorasDTO;
import com.augusto.testeperinity.DTOs.PessoaResumoDTO;
import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;


    //Adicionar uma pessoa (post/pessoas)
    @PostMapping
    public ResponseEntity<Object> createPessoa(@Valid @RequestBody Pessoa pessoa){

        try {
            Pessoa pessoaCriada = pessoaService.createPessoa(pessoa);
            return new ResponseEntity<>(pessoaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Listar pessoas trazendo nome, departamento, total de horas gastas nas tarefas.(get/pessoas)
    @GetMapping
    public ResponseEntity<List<PessoaResumoDTO>> getPessoasTotalHoras() {
        List<PessoaResumoDTO> pessoas = pessoaService.getPessoasTotalHoras();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }


    //Buscar pessoas por nome e período, retorna média de horas gastas por tarefa. (get/pessoas/gastos)
    @GetMapping("/gastos")
    public ResponseEntity<Object> getPessoaHorasPorPeriodo(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        try {
            PessoaMediaHorasDTO pessoaMediaHorasDTO = pessoaService.getPessoaHorasPorPeriodo(nome, dataInicio, dataFim);
            return new ResponseEntity<>(pessoaMediaHorasDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //Alterar uma pessoa (put/pessoas/{id})
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