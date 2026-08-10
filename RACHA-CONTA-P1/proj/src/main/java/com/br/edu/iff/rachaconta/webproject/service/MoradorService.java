package com.br.edu.iff.rachaconta.webproject.service;
import java.util.List;

 import org.springframework.stereotype.Service;

 import com.br.edu.iff.rachaconta.webproject.dto.MoradorDTO;
 import com.br.edu.iff.rachaconta.webproject.model.Morador;
 import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;
@Service public class MoradorService{
 private final MoradorRepository repo; private final CasaRepository casas;
 public MoradorService(MoradorRepository repo,CasaRepository casas){this.repo=repo;this.casas=casas;}
 public List<Morador> listar(){return repo.findAll();} public Morador buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Morador não encontrado."));}
 public Morador salvar(MoradorDTO d){validar(d); Morador m=new Morador(null,d.nome(),d.email(),d.ativo()); return salvar(m,d.casaId());}
 public Morador atualizar(Long id,MoradorDTO d){validar(d); Morador m=buscar(id); m.setNome(d.nome());m.setEmail(d.email());m.setAtivo(d.ativo()); desvincular(id); return salvar(m,d.casaId());}
 private Morador salvar(Morador m,Long casaId){Morador salvo=repo.save(m); if(casaId!=null){casas.findById(casaId).orElseThrow(()->new IllegalArgumentException("Casa não encontrada."));casas.findById(casaId).get().adicionarMorador(salvo.getId());} return salvo;}
 private void desvincular(Long id){casas.findAll().forEach(c->c.removerMorador(id));}
 public void excluir(Long id){desvincular(id);repo.deleteById(id);} private void validar(MoradorDTO d){if(d.nome()==null||d.nome().isBlank())throw new IllegalArgumentException("Nome é obrigatório.");if(d.email()==null||d.email().isBlank())throw new IllegalArgumentException("E-mail é obrigatório.");}
}
