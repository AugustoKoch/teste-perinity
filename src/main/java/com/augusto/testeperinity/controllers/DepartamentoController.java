package com.augusto.testeperinity.controllers;

import com.augusto.testeperinity.DTOs.DepartamentoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.services.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gerenciamento de Departamentos")
@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    DepartamentoService departamentoService;

    @Operation(summary = "Cria um novo departamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) {
        Departamento departamentoCriado = departamentoService.createDepartamento(departamento);
        return new ResponseEntity<>(departamentoCriado, HttpStatus.CREATED);
    }


    //Listar departamento e quantidade de pessoas e tarefas (get/departamentos)
    @Operation(summary = "Lista todos os departamentos com número de pessoas e tarefas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamentos listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum departamento encontrado")
    })
    @GetMapping
    public ResponseEntity<Object> getDepartamentos() {
        try {
            List<DepartamentoDTO> departamentos = departamentoService.getDepartamentos();
            return new ResponseEntity<>(departamentos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
