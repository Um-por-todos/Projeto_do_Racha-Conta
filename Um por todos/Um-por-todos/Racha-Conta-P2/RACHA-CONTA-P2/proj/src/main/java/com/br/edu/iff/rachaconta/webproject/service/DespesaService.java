package com.br.edu.iff.rachaconta.webproject.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.edu.iff.rachaconta.webproject.dto.DespesaDTO;
import com.br.edu.iff.rachaconta.webproject.exception.RecursoNaoEncontradoException;
import com.br.edu.iff.rachaconta.webproject.exception.RegraDeNegocioException;
import com.br.edu.iff.rachaconta.webproject.model.Casa;
import com.br.edu.iff.rachaconta.webproject.model.Despesa;
import com.br.edu.iff.rachaconta.webproject.model.Divida;
import com.br.edu.iff.rachaconta.webproject.model.Morador;
import com.br.edu.iff.rachaconta.webproject.repository.CasaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DespesaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.DividaRepository;
import com.br.edu.iff.rachaconta.webproject.repository.MoradorRepository;

@Service
public class DespesaService {

    private final DespesaRepository repository;
    private final CasaRepository casaRepository;
    private final MoradorRepository moradorRepository;
    private final DividaRepository dividaRepository;

    public DespesaService(
        DespesaRepository repository,
        CasaRepository casaRepository,
        MoradorRepository moradorRepository,
        DividaRepository dividaRepository
    ) {
        this.repository = repository;
        this.casaRepository = casaRepository;
        this.moradorRepository = moradorRepository;
        this.dividaRepository = dividaRepository;
    }

    public List<Despesa> listar() {
        return repository.findAll();
    }

    public Despesa buscar(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Despesa não encontrada."));
    }

    @Transactional
    public Despesa salvar(DespesaDTO dto) {
        validar(dto);

        Despesa despesa = new Despesa(
            null,
            dto.valorTotal(),
            dto.descricao(),
            dto.tipo(),
            dto.pagadorId(),
            dto.casaId(),
            dto.data() == null ? LocalDate.now() : dto.data()
        );

        Despesa salva = repository.save(despesa);
        gerarDividas(salva);
        return salva;
    }

    @Transactional
    public Despesa atualizar(Long id, DespesaDTO dto) {
        validar(dto);
        Despesa despesa = buscar(id);

        excluirDividasDaDespesa(id);

        despesa.setValorTotal(dto.valorTotal());
        despesa.setDescricao(dto.descricao());
        despesa.setTipo(dto.tipo());
        despesa.setPagadorId(dto.pagadorId());
        despesa.setCasaId(dto.casaId());
        despesa.setData(dto.data() == null ? LocalDate.now() : dto.data());

        Despesa atualizada = repository.save(despesa);
        gerarDividas(atualizada);
        return atualizada;
    }

    @Transactional
    public void excluir(Long id) {
        buscar(id);
        excluirDividasDaDespesa(id);
        repository.deleteById(id);
    }

    private void gerarDividas(Despesa despesa) {
        Casa casa = casaRepository.findById(despesa.getCasaId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Casa não encontrada."));

        List<Morador> moradoresAtivos = casa.getMoradoresIds().stream()
            .map(moradorRepository::findById)
            .flatMap(Optional::stream)
            .filter(Morador::isAtivo)
            .toList();

        if (moradoresAtivos.isEmpty()) {
            throw new RegraDeNegocioException("A casa não possui moradores ativos.");
        }

        boolean pagadorPertenceACasa = moradoresAtivos.stream()
            .anyMatch(morador -> morador.getId().equals(despesa.getPagadorId()));
        if (!pagadorPertenceACasa) {
            throw new RegraDeNegocioException("O pagador precisa ser um morador ativo da casa.");
        }

        BigDecimal parteIndividual = despesa.calcularValorIndividual(moradoresAtivos.size());
        List<Morador> devedores = moradoresAtivos.stream()
            .filter(morador -> !morador.getId().equals(despesa.getPagadorId()))
            .toList();

        gerarDividasDosMoradores(despesa, parteIndividual, devedores);
    }

    private void gerarDividasDosMoradores(
        Despesa despesa,
        BigDecimal parteIndividual,
        List<Morador> devedores
    ) {
        if (devedores.isEmpty()) {
            return;
        }

        BigDecimal totalDevido = despesa.getValorTotal().subtract(parteIndividual);
        BigDecimal somaGerada = BigDecimal.ZERO;

        for (int indice = 0; indice < devedores.size(); indice++) {
            Morador devedor = devedores.get(indice);
            BigDecimal valor = calcularParcelaDaDivida(
                indice,
                devedores.size(),
                totalDevido,
                somaGerada
            );

            somaGerada = somaGerada.add(valor);
            dividaRepository.save(new Divida(
                null,
                valor,
                false,
                despesa.getId(),
                devedor.getId(),
                despesa.getPagadorId()
            ));
        }
    }

    private BigDecimal calcularParcelaDaDivida(
        int indice,
        int quantidadeDevedores,
        BigDecimal totalDevido,
        BigDecimal somaGerada
    ) {
        boolean ultimoDevedor = indice == quantidadeDevedores - 1;
        if (ultimoDevedor) {
            return totalDevido.subtract(somaGerada);
        }

        return totalDevido.divide(
            BigDecimal.valueOf(quantidadeDevedores),
            2,
            RoundingMode.HALF_UP
        );
    }

    private void excluirDividasDaDespesa(Long despesaId) {
        dividaRepository.findAll().stream()
            .filter(divida -> despesaId.equals(divida.getDespesaId()))
            .map(Divida::getId)
            .toList()
            .forEach(dividaRepository::deleteById);
    }

    private void validar(DespesaDTO dto) {
        if (dto.valorTotal() == null || dto.valorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor inválido.");
        }
        if (dto.descricao() == null || dto.descricao().isBlank()) {
            throw new RegraDeNegocioException("Descrição é obrigatória.");
        }
        if (dto.tipo() == null || dto.tipo().isBlank()) {
            throw new RegraDeNegocioException("Tipo é obrigatório.");
        }
        if (dto.pagadorId() == null || dto.casaId() == null) {
            throw new RegraDeNegocioException("Casa e pagador são obrigatórios.");
        }
    }
}
