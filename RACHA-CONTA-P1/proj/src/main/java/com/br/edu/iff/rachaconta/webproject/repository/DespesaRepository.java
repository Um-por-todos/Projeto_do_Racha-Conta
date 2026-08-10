package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Despesa;
@Repository public class DespesaRepository extends InMemoryRepository<Despesa>{public DespesaRepository(){super(Despesa::getId,Despesa::setId);}}
