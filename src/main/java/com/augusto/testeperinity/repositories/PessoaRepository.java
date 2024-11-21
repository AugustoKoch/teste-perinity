package com.augusto.testeperinity.repositories;

import com.augusto.testeperinity.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}