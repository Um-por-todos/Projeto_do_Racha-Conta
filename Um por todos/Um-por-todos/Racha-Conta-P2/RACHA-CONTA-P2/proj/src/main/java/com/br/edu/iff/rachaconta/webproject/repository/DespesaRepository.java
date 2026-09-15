package com.br.edu.iff.rachaconta.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.iff.rachaconta.webproject.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
