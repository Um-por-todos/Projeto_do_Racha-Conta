package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioFinanceiro {
    private BigDecimal saldoTotal = BigDecimal.ZERO;
    private final List<Divida> dividasPendentes = new ArrayList<>();

    public RelatorioFinanceiro() {}

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public List<Divida> getDividasPendentes() {
        return List.copyOf(dividasPendentes);
    }

    public void gerarBalanco(List<Despesa> despesas, List<Divida> dividas) {
        saldoTotal = despesas.stream()
            .map(Despesa::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .subtract(dividas.stream()
                .map(Divida::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        dividasPendentes.clear();
        dividas.stream()
            .filter(d -> !d.isQuitada())
            .forEach(dividasPendentes::add);
    }

    public List<Divida> listarDividasPendentes(List<Divida> dividas) {
        return dividas.stream()
            .filter(d -> !d.isQuitada())
            .toList();
    }
}
