package com.br.edu.iff.rachaconta.webproject.service;
import java.math.BigDecimal;
 import java.math.RoundingMode;
 import java.time.LocalDate;
 import java.util.List;
 import java.util.Optional;

 import org.springframework.stereotype.Service;

 import com.br.edu.iff.rachaconta.webproject.dto.DespesaDTO;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DespesaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;
@Service public class DespesaService{private final DespesaRepository repo;private final CasaRepository casas;private final MoradorRepository moradores;private final DividaRepository dividas;public DespesaService(DespesaRepository repo,CasaRepository casas,MoradorRepository moradores,DividaRepository dividas){this.repo=repo;this.casas=casas;this.moradores=moradores;this.dividas=dividas;} public List<Despesa> listar(){return repo.findAll();}public Despesa buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Despesa não encontrada."));} public Despesa salvar(DespesaDTO d){validar(d);Despesa x=new Despesa(null,d.valorTotal(),d.descricao(),d.tipo(),d.pagadorId(),d.casaId(),d.data()==null?LocalDate.now():d.data());repo.save(x);gerarDividas(x);return x;} public Despesa atualizar(Long id,DespesaDTO d){validar(d);Despesa x=buscar(id);dividas.findAll().stream().filter(v->id.equals(v.getDespesaId())).map(Divida::getId).toList().forEach(dividas::deleteById);x.setValorTotal(d.valorTotal());x.setDescricao(d.descricao());x.setTipo(d.tipo());x.setPagadorId(d.pagadorId());x.setCasaId(d.casaId());x.setData(d.data()==null?LocalDate.now():d.data());repo.save(x);gerarDividas(x);return x;}public void excluir(Long id){dividas.findAll().stream().filter(v->id.equals(v.getDespesaId())).map(Divida::getId).toList().forEach(dividas::deleteById);repo.deleteById(id);} private void gerarDividas(Despesa x){Casa c=casas.findById(x.getCasaId()).orElseThrow();List<Morador> ativos=c.getMoradoresIds().stream().map(moradores::findById).flatMap(Optional::stream).filter(Morador::isAtivo).toList();if(ativos.isEmpty())throw new IllegalArgumentException("A casa não possui moradores ativos.");if(!ativos.stream().anyMatch(m->m.getId().equals(x.getPagadorId())))throw new IllegalArgumentException("O pagador precisa ser um morador ativo da casa.");BigDecimal parte=x.calcularValorIndividual(ativos.size());List<Morador> devedores=ativos.stream().filter(m->!m.getId().equals(x.getPagadorId())).toList();BigDecimal soma=BigDecimal.ZERO;BigDecimal totalDevido=x.getValorTotal().subtract(parte);for(int i=0;i<devedores.size();i++){Morador m=devedores.get(i);BigDecimal p=i==devedores.size()-1?totalDevido.subtract(soma):totalDevido.divide(BigDecimal.valueOf(devedores.size()),2,RoundingMode.HALF_UP);soma=soma.add(p);dividas.save(new Divida(null,p,false,x.getId(),m.getId(),x.getPagadorId()));}} private void validar(DespesaDTO d){if(d.valorTotal()==null||d.valorTotal().compareTo(BigDecimal.ZERO)<=0)throw new IllegalArgumentException("Valor inválido.");}
}
