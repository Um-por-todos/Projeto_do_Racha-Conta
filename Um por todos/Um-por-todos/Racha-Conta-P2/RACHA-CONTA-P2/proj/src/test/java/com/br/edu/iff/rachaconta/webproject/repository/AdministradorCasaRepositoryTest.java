package com.br.edu.iff.rachaconta.webproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.edu.iff.rachaconta.webproject.model.AdministradorCasa;

@DataJpaTest
class AdministradorCasaRepositoryTest {

    @Autowired
    private AdministradorCasaRepository repository;

    @Test
    void deveGravarEBuscarAdministrador() {
        AdministradorCasa salvo = repository.saveAndFlush(new AdministradorCasa(
            null,
            "Administrador Teste",
            "admin.teste@email.com",
            "TOTAL"
        ));

        assertNotNull(salvo.getId());

        AdministradorCasa encontrado = repository.findById(salvo.getId()).orElseThrow();
        assertEquals("Administrador Teste", encontrado.getNome());
        assertEquals("TOTAL", encontrado.getNivelAcesso());
    }

    @Test
    void deveImpedirEmailDuplicado() {
        repository.saveAndFlush(new AdministradorCasa(
            null,
            "Primeiro Administrador",
            "admin.duplicado@email.com",
            "TOTAL"
        ));

        AdministradorCasa duplicado = new AdministradorCasa(
            null,
            "Segundo Administrador",
            "admin.duplicado@email.com",
            "PARCIAL"
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(duplicado)
        );
    }
}
