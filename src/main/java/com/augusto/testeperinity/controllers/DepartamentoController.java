package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    DepartamentoService departamentoService;

    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) {
        Departamento departamentoCriado = departamentoService.createDepartamento(departamento);
        return new ResponseEntity<>(departamentoCriado, HttpStatus.CREATED);
    }
}
