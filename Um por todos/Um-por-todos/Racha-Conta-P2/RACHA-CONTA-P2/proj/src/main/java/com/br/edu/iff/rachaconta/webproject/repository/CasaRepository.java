package com.br.edu.iff.rachaconta.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.iff.rachaconta.webproject.model.Casa;

public interface CasaRepository extends JpaRepository<Casa, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
