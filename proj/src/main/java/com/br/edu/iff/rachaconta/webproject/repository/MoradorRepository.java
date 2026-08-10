package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Morador;
@Repository public class MoradorRepository extends InMemoryRepository<Morador>{public MoradorRepository(){super(Morador::getId,Morador::setId);}}
