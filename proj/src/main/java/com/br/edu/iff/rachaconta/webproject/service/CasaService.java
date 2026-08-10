package com.br.edu.iff.rachaconta.webproject.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.edu.iff.rachaconta.webproject.dto.CasaDTO;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;
@Service public class CasaService{private final CasaRepository repo; private final MoradorRepository moradores; public CasaService(CasaRepository repo,MoradorRepository moradores){this.repo=repo;this.moradores=moradores;} public List<Casa> listar(){return repo.findAll();} public Casa buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Casa não encontrada."));} public Casa salvar(CasaDTO d){if(d.nome()==null||d.nome().isBlank())throw new IllegalArgumentException("Nome é obrigatório.");return repo.save(new Casa(null,d.nome(),d.endereco()));} public Casa atualizar(Long id,CasaDTO d){Casa c=buscar(id);c.setNome(d.nome());c.setEndereco(d.endereco());return repo.save(c);} public void adicionarMorador(Long casaId,Long moradorId){Casa c=buscar(casaId);moradores.findById(moradorId).orElseThrow(()->new IllegalArgumentException("Morador não encontrado."));c.adicionarMorador(moradorId);repo.save(c);} public List<Morador> listarMoradores(Long casaId){Casa c=buscar(casaId);return c.getMoradoresIds().stream().map(moradores::findById).flatMap(Optional::stream).toList();} public void excluir(Long id){repo.deleteById(id);}}
