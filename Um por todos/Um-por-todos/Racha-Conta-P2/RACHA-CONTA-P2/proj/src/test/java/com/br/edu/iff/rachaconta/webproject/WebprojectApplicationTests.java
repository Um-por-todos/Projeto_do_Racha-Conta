package com.br.edu.iff.rachaconta.webproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Pagamento;

class WebprojectApplicationTests {

    @Test
    void despesaCalculaValorIndividual() {
        Despesa despesa = new Despesa();
        despesa.setValorTotal(new BigDecimal("200.00"));

        assertEquals(new BigDecimal("40.00"), despesa.calcularValorIndividual(5));
    }

    @Test
    void dividaPodeSerMarcadaComoPaga() {
        Divida divida = new Divida();

        assertFalse(divida.isQuitada());
        divida.marcarComoPaga();

        assertTrue(divida.isQuitada());
        assertEquals("Paga", divida.verificarStatus());
    }

    @Test
    void pagamentoPodeSerConfirmado() {
        Pagamento pagamento = new Pagamento();

        assertFalse(pagamento.isConfirmado());
        pagamento.confirmarPagamento();

        assertTrue(pagamento.isConfirmado());
    }

    @Test
    void casaAdicionaEListaMoradorSemDuplicar() {
        Casa casa = new Casa();

        casa.adicionarMorador(10L);
        casa.adicionarMorador(10L);

        assertEquals(List.of(10L), casa.listarMoradores());
    }
}
