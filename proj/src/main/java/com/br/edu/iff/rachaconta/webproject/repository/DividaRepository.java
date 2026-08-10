package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Divida;
@Repository public class DividaRepository extends InMemoryRepository<Divida>{public DividaRepository(){super(Divida::getId,Divida::setId);}}
