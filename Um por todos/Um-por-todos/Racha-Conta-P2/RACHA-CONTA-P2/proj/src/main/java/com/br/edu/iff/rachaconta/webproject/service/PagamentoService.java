package com.br.edu.iff.rachaconta.webproject.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.edu.iff.rachaconta.webproject.dto.PagamentoDTO;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.exception.RegraDeNegocioException;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Pagamento;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.PagamentoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final DividaRepository dividaRepository;

    public PagamentoService(
        PagamentoRepository repository,
        DividaRepository dividaRepository
    ) {
        this.repository = repository;
        this.dividaRepository = dividaRepository;
    }

    public List<Pagamento> listar() {
        return repository.findAll();
    }

    public Pagamento buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado."));
    }

    @Transactional
    public Pagamento salvar(PagamentoDTO dto) {
        Divida divida = buscarDivida(dto.dividaId());
        validarValor(dto.valorPago(), divida);

        Pagamento pagamento = new Pagamento(
            null,
            dto.valorPago(),
            dto.dataPagamento() == null ? LocalDate.now() : dto.dataPagamento(),
            dto.dividaId(),
            dto.confirmado()
        );

        if (pagamento.isConfirmado()) {
            quitarDivida(divida);
        }

        return repository.save(pagamento);
    }

    @Transactional
    public Pagamento atualizar(Long id, PagamentoDTO dto) {
        Pagamento pagamento = buscar(id);
        Divida divida = buscarDivida(dto.dividaId());
        validarValor(dto.valorPago(), divida);

        pagamento.setValorPago(dto.valorPago());
        pagamento.setDataPagamento(
            dto.dataPagamento() == null ? LocalDate.now() : dto.dataPagamento()
        );
        pagamento.setDividaId(dto.dividaId());
        pagamento.setConfirmado(dto.confirmado());

        if (pagamento.isConfirmado()) {
            quitarDivida(divida);
        }

        return repository.save(pagamento);
    }

    @Transactional
    public void confirmar(Long id) {
        Pagamento pagamento = buscar(id);
        Divida divida = buscarDivida(pagamento.getDividaId());

        pagamento.confirmarPagamento();
        quitarDivida(divida);
        repository.save(pagamento);
    }

    public void excluir(Long id) {
        buscar(id);
        repository.deleteById(id);
    }

    private Divida buscarDivida(Long dividaId) {
        if (dividaId == null) {
            throw new RegraDeNegocioException("Dívida é obrigatória.");
        }
        return dividaRepository.findById(dividaId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Dívida não encontrada."));
    }

    private void validarValor(BigDecimal valorPago, Divida divida) {
        if (valorPago == null || valorPago.compareTo(divida.getValor()) < 0) {
            throw new RegraDeNegocioException("O pagamento deve quitar o valor total da dívida.");
        }
    }

    private void quitarDivida(Divida divida) {
        divida.marcarComoPaga();
        dividaRepository.save(divida);
    }
}
