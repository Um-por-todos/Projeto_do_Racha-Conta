package com.br.edu.iff.rachaconta.webproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.edu.iff.rachaconta.webproject.dto.CasaDTO;
import com.br.edu.iff.rachaconta.webproject.exception.EntidadeDuplicadaException;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;

@Service
public class CasaService {

    private final CasaRepository repository;
    private final MoradorRepository moradorRepository;

    public CasaService(CasaRepository repository, MoradorRepository moradorRepository) {
        this.repository = repository;
        this.moradorRepository = moradorRepository;
    }

    public List<Casa> listar() {
        return repository.findAll();
    }

    public Casa buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Casa não encontrada."));
    }

    public Casa salvar(CasaDTO dto) {
        validarNomeDuplicado(dto.nome(), null);
        return repository.save(new Casa(null, dto.nome().trim(), dto.endereco().trim()));
    }

    public Casa atualizar(Long id, CasaDTO dto) {
        Casa casa = buscar(id);
        validarNomeDuplicado(dto.nome(), id);

        casa.setNome(dto.nome().trim());
        casa.setEndereco(dto.endereco().trim());
        return repository.save(casa);
    }

    @Transactional
    public void adicionarMorador(Long casaId, Long moradorId) {
        Casa casa = buscar(casaId);
        moradorRepository.findById(moradorId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Morador não encontrado."));

        casa.adicionarMorador(moradorId);
        repository.save(casa);
    }

    public List<Morador> listarMoradores(Long casaId) {
        Casa casa = buscar(casaId);
        return casa.getMoradoresIds().stream()
            .map(moradorRepository::findById)
            .flatMap(Optional::stream)
            .toList();
    }

    public void excluir(Long id) {
        buscar(id);
        repository.deleteById(id);
    }

    private void validarNomeDuplicado(String nome, Long idAtual) {
        boolean duplicado = idAtual == null
            ? repository.existsByNomeIgnoreCase(nome)
            : repository.existsByNomeIgnoreCaseAndIdNot(nome, idAtual);

        if (duplicado) {
            throw new EntidadeDuplicadaException("Já existe uma casa com este nome.");
        }
    }
}
