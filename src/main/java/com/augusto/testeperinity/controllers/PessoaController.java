package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.entities.Pessoa;
import com.augusto.testeperinity.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Object> createPessoa(@RequestBody Pessoa pessoa){

        try {
            Pessoa pessoaCriada = pessoaService.createPessoa(pessoa);
            return new ResponseEntity<>(pessoaCriada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa){
        try {
            Pessoa pessoaAlterada = pessoaService.updatePessoa(id, pessoa);
            return new ResponseEntity<>(pessoaAlterada, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

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