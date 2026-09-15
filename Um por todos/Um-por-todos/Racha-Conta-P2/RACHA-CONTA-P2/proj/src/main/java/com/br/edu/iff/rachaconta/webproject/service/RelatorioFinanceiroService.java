package com.br.edu.iff.rachaconta.webproject.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.model.RelatorioFinanceiro;
import com.br.edu.iff.rachaconta.webproject.repository.DespesaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;

@Service
public class RelatorioFinanceiroService {

    private final DespesaRepository despesaRepository;
    private final DividaRepository dividaRepository;
    private final MoradorRepository moradorRepository;

    public RelatorioFinanceiroService(
        DespesaRepository despesaRepository,
        DividaRepository dividaRepository,
        MoradorRepository moradorRepository
    ) {
        this.despesaRepository = despesaRepository;
        this.dividaRepository = dividaRepository;
        this.moradorRepository = moradorRepository;
    }

    public RelatorioFinanceiro gerar() {
        RelatorioFinanceiro relatorio = new RelatorioFinanceiro();
        relatorio.gerarBalanco(
            despesaRepository.findAll(),
            dividaRepository.findAll()
        );
        return relatorio;
    }

    public List<Divida> pendentes() {
        return new RelatorioFinanceiro()
            .listarDividasPendentes(dividaRepository.findAll());
    }

    public String nome(Long id) {
        return moradorRepository.findById(id)
            .map(Morador::getNome)
            .orElse("Desconhecido");
    }

    public BigDecimal saldoDe(Long id) {
        return pendentes().stream()
            .filter(divida -> id.equals(divida.getDevedorId()))
            .map(Divida::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
