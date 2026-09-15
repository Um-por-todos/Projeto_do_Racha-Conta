package com.br.edu.iff.rachaconta.webproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.edu.iff.rachaconta.webproject.dto.AdministradorCasaDTO;
import com.br.edu.iff.rachaconta.webproject.exception.EntidadeDuplicadaException;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.model.AdministradorCasa;
import com.br.edu.iff.rachaconta.webproject.repository.AdministradorCasaRepository;

@Service
public class AdministradorCasaService {

    private final AdministradorCasaRepository repository;

    public AdministradorCasaService(AdministradorCasaRepository repository) {
        this.repository = repository;
    }

    public List<AdministradorCasa> listar() {
        return repository.findAll();
    }

    public AdministradorCasa buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado."));
    }

    public AdministradorCasa salvar(AdministradorCasaDTO dto) {
        validarEmailDuplicado(dto.email(), null);

        AdministradorCasa administrador = new AdministradorCasa(
            null,
            dto.nome().trim(),
            dto.email().trim(),
            dto.nivelAcesso().trim()
        );
        return repository.save(administrador);
    }

    public AdministradorCasa atualizar(Long id, AdministradorCasaDTO dto) {
        AdministradorCasa administrador = buscar(id);
        validarEmailDuplicado(dto.email(), id);

        administrador.setNome(dto.nome().trim());
        administrador.setEmail(dto.email().trim());
        administrador.setNivelAcesso(dto.nivelAcesso().trim());
        return repository.save(administrador);
    }

    public void excluir(Long id) {
        buscar(id);
        repository.deleteById(id);
    }

    private void validarEmailDuplicado(String email, Long idAtual) {
        boolean duplicado = idAtual == null
            ? repository.existsByEmailIgnoreCase(email)
            : repository.existsByEmailIgnoreCaseAndIdNot(email, idAtual);

        if (duplicado) {
            throw new EntidadeDuplicadaException("Já existe um administrador com este e-mail.");
        }
    }
}
