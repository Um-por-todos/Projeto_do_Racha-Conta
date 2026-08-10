package com.br.edu.iff.rachaconta.webproject.service;
import java.util.List;

 import org.springframework.stereotype.Service;

 import com.br.edu.iff.rachaconta.webproject.dto.AdministradorCasaDTO;
 import com.br.edu.iff.rachaconta.webproject.model.AdministradorCasa;
 import com.br.edu.iff.rachaconta.webproject.repository.AdministradorCasaRepository;
@Service public class AdministradorCasaService{private final AdministradorCasaRepository repo; public AdministradorCasaService(AdministradorCasaRepository repo){this.repo=repo;} public List<AdministradorCasa> listar(){return repo.findAll();} public AdministradorCasa buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Administrador não encontrado."));} public AdministradorCasa salvar(AdministradorCasaDTO d){validar(d);return repo.save(new AdministradorCasa(null,d.nome(),d.email(),d.nivelAcesso()));} public AdministradorCasa atualizar(Long id,AdministradorCasaDTO d){validar(d);AdministradorCasa a=buscar(id);a.setNome(d.nome());a.setEmail(d.email());a.setNivelAcesso(d.nivelAcesso());return repo.save(a);} public void excluir(Long id){repo.deleteById(id);} private void validar(AdministradorCasaDTO d){if(d.nome()==null||d.nome().isBlank())throw new IllegalArgumentException("Nome é obrigatório.");}}
