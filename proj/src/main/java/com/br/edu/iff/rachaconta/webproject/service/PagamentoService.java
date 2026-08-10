package com.br.edu.iff.rachaconta.webproject.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.edu.iff.rachaconta.webproject.dto.PagamentoDTO;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Pagamento;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.PagamentoRepository;
@Service public class PagamentoService{private final PagamentoRepository repo;private final DividaRepository dividas;public PagamentoService(PagamentoRepository repo,DividaRepository dividas){this.repo=repo;this.dividas=dividas;}public List<Pagamento> listar(){return repo.findAll();}public Pagamento buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Pagamento não encontrado."));}public Pagamento salvar(PagamentoDTO d){Divida div=dividas.findById(d.dividaId()).orElseThrow(()->new IllegalArgumentException("Dívida não encontrada."));if(d.valorPago()==null||d.valorPago().compareTo(div.getValor())<0)throw new IllegalArgumentException("O pagamento deve quitar o valor total da dívida.");Pagamento p=new Pagamento(null,d.valorPago(),d.dataPagamento()==null?java.time.LocalDate.now():d.dataPagamento(),d.dividaId(),d.confirmado());if(p.isConfirmado()){div.marcarComoPaga();dividas.save(div);}return repo.save(p);}public Pagamento atualizar(Long id,PagamentoDTO d){Pagamento p=buscar(id);p.setValorPago(d.valorPago());p.setDataPagamento(d.dataPagamento());p.setDividaId(d.dividaId());p.setConfirmado(d.confirmado());if(p.isConfirmado()){Divida div=dividas.findById(p.getDividaId()).orElseThrow();div.marcarComoPaga();dividas.save(div);}return repo.save(p);}public void confirmar(Long id){Pagamento p=buscar(id);p.confirmarPagamento();Divida d=dividas.findById(p.getDividaId()).orElseThrow();d.marcarComoPaga();dividas.save(d);repo.save(p);}public void excluir(Long id){repo.deleteById(id);}}
