package com.br.edu.iff.rachaconta.webproject.service;
import java.util.List;

 import org.springframework.stereotype.Service;

 import com.br.edu.iff.rachaconta.webproject.dto.DividaDTO;
 import com.br.edu.iff.rachaconta.webproject.model.Divida;
 import com.br.edu.iff.rachaconta.webproject.repository.DespesaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;
@Service public class DividaService{private final DividaRepository repo;private final DespesaRepository despesas;private final MoradorRepository moradores;public DividaService(DividaRepository repo,DespesaRepository despesas,MoradorRepository moradores){this.repo=repo;this.despesas=despesas;this.moradores=moradores;}public List<Divida> listar(){return repo.findAll();}public Divida buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Dívida não encontrada."));}public Divida salvar(DividaDTO d){despesas.findById(d.despesaId()).orElseThrow();moradores.findById(d.devedorId()).orElseThrow();moradores.findById(d.credorId()).orElseThrow();return repo.save(new Divida(null,d.valor(),false,d.despesaId(),d.devedorId(),d.credorId()));}public Divida atualizar(Long id,DividaDTO d){Divida x=buscar(id);x.setValor(d.valor());x.setDevedorId(d.devedorId());x.setCredorId(d.credorId());return repo.save(x);}public void marcarComoPaga(Long id){Divida d=buscar(id);d.marcarComoPaga();repo.save(d);}public void excluir(Long id){repo.deleteById(id);}}
