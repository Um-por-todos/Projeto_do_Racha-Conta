package com.br.edu.iff.rachaconta.webproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.edu.iff.rachaconta.webproject.model.Casa;

@DataJpaTest
class CasaRepositoryTest {

    @Autowired
    private CasaRepository repository;

    @Test
    void deveGravarEBuscarCasa() {
        Casa salvo = repository.saveAndFlush(new Casa(
            null,
            "Casa Central",
            "Rua das Flores, 120"
        ));

        assertNotNull(salvo.getId());

        Casa encontrada = repository.findById(salvo.getId()).orElseThrow();
        assertEquals("Casa Central", encontrada.getNome());
        assertEquals("Rua das Flores, 120", encontrada.getEndereco());
    }

    @Test
    void deveImpedirNomeDuplicado() {
        repository.saveAndFlush(new Casa(
            null,
            "Republica Teste",
            "Rua A, 100"
        ));

        Casa duplicada = new Casa(
            null,
            "Republica Teste",
            "Rua B, 200"
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(duplicada)
        );
    }
}
