package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.DepartamentoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    //Listar departamento e quantidade de pessoas e tarefas (get/departamentos)
    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> getDepartamentos() {
        List<DepartamentoDTO> departamentos = departamentoService.getDepartamentos();
        return new ResponseEntity<>(departamentos, HttpStatus.OK);
    }
}
