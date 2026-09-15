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

import com.br.edu.iff.rachaconta.webproject.model.Pagamento;

@DataJpaTest
class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository repository;

    @Test
    void deveGravarEBuscarPagamento() {
        Pagamento salvo = repository.saveAndFlush(new Pagamento(
            null,
            new BigDecimal("90.25"),
            LocalDate.of(2026, 9, 11),
            1L,
            true
        ));

        assertNotNull(salvo.getId());

        Pagamento encontrado = repository.findById(salvo.getId()).orElseThrow();
        assertEquals(new BigDecimal("90.25"), encontrado.getValorPago());
        assertEquals(true, encontrado.isConfirmado());
    }

    @Test
    void deveImpedirValorPagoNulo() {
        Pagamento invalido = new Pagamento(
            null,
            null,
            LocalDate.of(2026, 9, 11),
            1L,
            false
        );

        assertThrows(
            DataIntegrityViolationException.class,
            () -> repository.saveAndFlush(invalido)
        );
    }
}
