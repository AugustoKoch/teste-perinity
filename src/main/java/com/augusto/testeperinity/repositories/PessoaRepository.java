package com.augusto.testeperinity.repositories;

import com.augusto.testeperinity.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findPessoaByNome(String nome);
}