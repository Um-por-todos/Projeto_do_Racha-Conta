package com.br.edu.iff.rachaconta.webproject;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Pagamento;

class WebprojectApplicationTests {
 @Test void despesaCalculaValorIndividual(){Despesa d=new Despesa();d.setValorTotal(new BigDecimal("200.00"));assertEquals(new BigDecimal("40.00"),d.calcularValorIndividual(5));}
 @Test void dividaPodeSerMarcadaComoPaga(){Divida d=new Divida();assertFalse(d.isQuitada());d.marcarComoPaga();assertTrue(d.isQuitada());assertEquals("Paga",d.verificarStatus());}
 @Test void pagamentoPodeSerConfirmado(){Pagamento p=new Pagamento();assertFalse(p.isConfirmado());p.confirmarPagamento();assertTrue(p.isConfirmado());}
 @Test void casaAdicionaEListaMorador(){Casa c=new Casa();c.adicionarMorador(10L);c.adicionarMorador(10L);assertEquals(List.of(10L),c.listarMoradores());}
}
