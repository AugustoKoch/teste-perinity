package com.augusto.testeperinity.services;

import com.augusto.testeperinity.DTOs.DepartamentoDTO;
import com.augusto.testeperinity.entities.Departamento;
import com.augusto.testeperinity.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;


    public Departamento createDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }


    public List<DepartamentoDTO> getDepartamentos(){

        List<Departamento> departamentos = departamentoRepository.findAll();

        List<DepartamentoDTO> departamentoDTOList = new ArrayList<>();

        for (Departamento departamento : departamentos) {
            DepartamentoDTO departamentoDTO = new DepartamentoDTO(departamento.getNome(),
                    departamento.getPessoas().size(),
                    departamento.getTarefas().size());

            departamentoDTOList.add(departamentoDTO);
        }
        return departamentoDTOList;
    }
}
