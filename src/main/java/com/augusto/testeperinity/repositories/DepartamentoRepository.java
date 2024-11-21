package com.augusto.testeperinity.repositories;

import com.augusto.testeperinity.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository  extends JpaRepository<Departamento, Long> {
}
