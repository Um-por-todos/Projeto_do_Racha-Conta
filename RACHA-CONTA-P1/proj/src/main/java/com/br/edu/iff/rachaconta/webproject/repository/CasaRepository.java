package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Casa;
@Repository public class CasaRepository extends InMemoryRepository<Casa>{public CasaRepository(){super(Casa::getId,Casa::setId);}}
