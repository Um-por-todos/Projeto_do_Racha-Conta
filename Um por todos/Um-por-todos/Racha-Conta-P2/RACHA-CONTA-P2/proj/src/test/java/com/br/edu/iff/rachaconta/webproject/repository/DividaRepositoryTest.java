package com.br.edu.iff.rachaconta.webproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.edu.iff.rachaconta.webproject.model.Divida;

@DataJpaTest
class DividaRepositoryTest {

    @Autowired
    private DividaRepository repository;

    @Test
    void deveGravarEBuscarDivida() {
        Divida salvo = repository.saveAndFlush(new Divida(
            null,
            new BigDecimal("90.25"),
            false,
            1L,
            2L,
            1L
        ));

        assertNotNull(salvo.getId());

        Divida encontrada = repository.findById(salvo.getId()).orElseThrow();
        assertEquals(new BigDecimal("90.25"), encontrada.getValor());
        assertEquals(false, encontrada.isQuitada());
    }

    @Test
    void deveImpedirValorNulo() {
        Divida invalida = new Divida(
            null,
            null,
            false,
            1L,
            2L,
            1L
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(invalida)
        );
    }
}
