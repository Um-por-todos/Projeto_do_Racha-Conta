package com.br.edu.iff.rachaconta.webproject.repository;
import org.springframework.stereotype.Repository;

import com.br.edu.iff.rachaconta.webproject.model.Usuario;
@Repository public class UsuarioRepository extends InMemoryRepository<Usuario>{public UsuarioRepository(){super(Usuario::getId,Usuario::setId);}}
