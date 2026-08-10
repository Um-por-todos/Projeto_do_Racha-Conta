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
@Service public class RelatorioFinanceiroService{private final DespesaRepository despesas;private final DividaRepository dividas;private final MoradorRepository moradores;public RelatorioFinanceiroService(DespesaRepository d,DividaRepository v,MoradorRepository m){despesas=d;dividas=v;moradores=m;}public RelatorioFinanceiro gerar(){RelatorioFinanceiro r=new RelatorioFinanceiro();r.gerarBalanco(despesas.findAll(),dividas.findAll());return r;}public List<Divida> pendentes(){return new RelatorioFinanceiro().listarDividasPendentes(dividas.findAll());}public String nome(Long id){return moradores.findById(id).map(Morador::getNome).orElse("Desconhecido");}public BigDecimal saldoDe(Long id){return pendentes().stream().filter(d->id.equals(d.getDevedorId())).map(Divida::getValor).reduce(BigDecimal.ZERO,BigDecimal::add);}}
