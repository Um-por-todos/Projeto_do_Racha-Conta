package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.AdministradorCasa;
@Repository public class AdministradorCasaRepository extends InMemoryRepository<AdministradorCasa>{public AdministradorCasaRepository(){super(AdministradorCasa::getId,AdministradorCasa::setId);}}
