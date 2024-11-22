package com.augusto.testeperinity.services;

import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;

    public Departamento createDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
}
