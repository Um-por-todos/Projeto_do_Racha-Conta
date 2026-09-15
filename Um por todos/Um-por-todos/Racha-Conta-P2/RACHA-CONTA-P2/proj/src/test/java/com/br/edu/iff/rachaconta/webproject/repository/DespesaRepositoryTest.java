package com.br.edu.iff.rachaconta.webproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.edu.iff.rachaconta.webproject.model.Despesa;

@DataJpaTest
class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository repository;

    @Test
    void deveGravarEBuscarDespesa() {
        Despesa salvo = repository.saveAndFlush(new Despesa(
            null,
            new BigDecimal("180.50"),
            "Supermercado",
            "ALIMENTACAO",
            1L,
            1L,
            LocalDate.of(2026, 9, 11)
        ));

        assertNotNull(salvo.getId());

        Despesa encontrada = repository.findById(salvo.getId()).orElseThrow();
        assertEquals(new BigDecimal("180.50"), encontrada.getValorTotal());
        assertEquals("Supermercado", encontrada.getDescricao());
    }

    @Test
    void deveImpedirDescricaoNula() {
        Despesa invalida = new Despesa(
            null,
            new BigDecimal("50.00"),
            null,
            "OUTROS",
            1L,
            1L,
            LocalDate.of(2026, 9, 11)
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(invalida)
        );
    }
}
