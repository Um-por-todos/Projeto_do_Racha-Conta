package com.br.edu.iff.rachaconta.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.iff.rachaconta.webproject.model.Morador;

public interface MoradorRepository extends JpaRepository<Morador, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
