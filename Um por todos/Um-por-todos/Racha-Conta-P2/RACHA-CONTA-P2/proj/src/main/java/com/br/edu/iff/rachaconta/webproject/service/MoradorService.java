package com.br.edu.iff.rachaconta.webproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.edu.iff.rachaconta.webproject.dto.MoradorDTO;
import com.br.edu.iff.rachaconta.webproject.exception.EntidadeDuplicadaException;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;

@Service
public class MoradorService {

    private final MoradorRepository repository;
    private final CasaRepository casaRepository;

    public MoradorService(MoradorRepository repository, CasaRepository casaRepository) {
        this.repository = repository;
        this.casaRepository = casaRepository;
    }

    public List<Morador> listar() {
        return repository.findAll();
    }

    public Morador buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Morador não encontrado."));
    }

    @Transactional
    public Morador salvar(MoradorDTO dto) {
        validarEmailDuplicado(dto.email(), null);
        Morador morador = new Morador(null, dto.nome().trim(), dto.email().trim(), dto.ativo());
        return salvarEVincular(morador, dto.casaId());
    }

    @Transactional
    public Morador atualizar(Long id, MoradorDTO dto) {
        Morador morador = buscar(id);
        validarEmailDuplicado(dto.email(), id);

        morador.setNome(dto.nome().trim());
        morador.setEmail(dto.email().trim());
        morador.setAtivo(dto.ativo());

        desvincularDasCasas(id);
        return salvarEVincular(morador, dto.casaId());
    }

    @Transactional
    public void excluir(Long id) {
        buscar(id);
        desvincularDasCasas(id);
        repository.deleteById(id);
    }

    private Morador salvarEVincular(Morador morador, Long casaId) {
        Morador salvo = repository.save(morador);

        if (casaId == null) {
            return salvo;
        }

        Casa casa = casaRepository.findById(casaId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Casa não encontrada."));
        casa.adicionarMorador(salvo.getId());
        casaRepository.save(casa);

        return salvo;
    }

    private void desvincularDasCasas(Long moradorId) {
        casaRepository.findAll().stream()
            .filter(casa -> casa.getMoradoresIds().contains(moradorId))
            .forEach(casa -> {
                casa.removerMorador(moradorId);
                casaRepository.save(casa);
            });
    }

    private void validarEmailDuplicado(String email, Long idAtual) {
        boolean duplicado = idAtual == null
            ? repository.existsByEmailIgnoreCase(email)
            : repository.existsByEmailIgnoreCaseAndIdNot(email, idAtual);

        if (duplicado) {
            throw new EntidadeDuplicadaException("Já existe um morador com este e-mail.");
        }
    }
}
