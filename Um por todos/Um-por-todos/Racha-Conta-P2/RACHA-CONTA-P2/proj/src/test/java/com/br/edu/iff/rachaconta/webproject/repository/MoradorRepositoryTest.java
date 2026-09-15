package com.br.edu.iff.rachaconta.webproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.edu.iff.rachaconta.webproject.model.Morador;

@DataJpaTest
class MoradorRepositoryTest {

    @Autowired
    private MoradorRepository repository;

    @Test
    void deveGravarMoradorComSucesso() {
        Morador morador = new Morador(
            null,
            "Renan",
            "renan.teste@email.com",
            true
        );

        Morador salvo = repository.saveAndFlush(morador);

        assertNotNull(salvo.getId());
        assertEquals("Renan", salvo.getNome());
        assertTrue(salvo.isAtivo());
    }

    @Test
    void deveBuscarMoradorPorId() {
        Morador salvo = repository.saveAndFlush(new Morador(
            null,
            "Victor",
            "victor.teste@email.com",
            true
        ));

        Morador encontrado = repository.findById(salvo.getId()).orElseThrow();

        assertEquals(salvo.getId(), encontrado.getId());
        assertEquals("Victor", encontrado.getNome());
        assertEquals("victor.teste@email.com", encontrado.getEmail());
    }

    @Test
    void deveLancarExcecaoAoSalvarEmailDuplicado() {
        repository.saveAndFlush(new Morador(
            null,
            "Primeiro Morador",
            "duplicado@email.com",
            true
        ));

        Morador duplicado = new Morador(
            null,
            "Segundo Morador",
            "duplicado@email.com",
            true
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(duplicado)
        );
    }

    @Test
    void deveLancarExcecaoAoSalvarNomeNulo() {
        Morador invalido = new Morador(
            null,
            null,
            "sem.nome@email.com",
            true
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(invalido)
        );
    }
}
