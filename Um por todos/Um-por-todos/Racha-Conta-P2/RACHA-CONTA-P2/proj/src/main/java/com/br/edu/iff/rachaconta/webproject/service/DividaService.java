package com.br.edu.iff.rachaconta.webproject.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.edu.iff.rachaconta.webproject.dto.DividaDTO;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.exception.RegraDeNegocioException;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.repository.DespesaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;

@Service
public class DividaService {

    private final DividaRepository repository;
    private final DespesaRepository despesaRepository;
    private final MoradorRepository moradorRepository;

    public DividaService(
        DividaRepository repository,
        DespesaRepository despesaRepository,
        MoradorRepository moradorRepository
    ) {
        this.repository = repository;
        this.despesaRepository = despesaRepository;
        this.moradorRepository = moradorRepository;
    }

    public List<Divida> listar() {
        return repository.findAll();
    }

    public Divida buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Dívida não encontrada."));
    }

    public Divida salvar(DividaDTO dto) {
        validar(dto);
        validarReferencias(dto);
        return repository.save(new Divida(
            null,
            dto.valor(),
            false,
            dto.despesaId(),
            dto.devedorId(),
            dto.credorId()
        ));
    }

    public Divida atualizar(Long id, DividaDTO dto) {
        validar(dto);
        validarReferencias(dto);
        Divida divida = buscar(id);
        divida.setValor(dto.valor());
        divida.setDevedorId(dto.devedorId());
        divida.setCredorId(dto.credorId());
        return repository.save(divida);
    }

    public void marcarComoPaga(Long id) {
        Divida divida = buscar(id);
        divida.marcarComoPaga();
        repository.save(divida);
    }

    public void excluir(Long id) {
        buscar(id);
        repository.deleteById(id);
    }

    private void validarReferencias(DividaDTO dto) {
        despesaRepository.findById(dto.despesaId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Despesa não encontrada."));
        moradorRepository.findById(dto.devedorId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Devedor não encontrado."));
        moradorRepository.findById(dto.credorId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Credor não encontrado."));
    }

    private void validar(DividaDTO dto) {
        if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor da dívida deve ser maior que zero.");
        }
        if (dto.despesaId() == null || dto.devedorId() == null || dto.credorId() == null) {
            throw new RegraDeNegocioException("Despesa, devedor e credor são obrigatórios.");
        }
    }
}
