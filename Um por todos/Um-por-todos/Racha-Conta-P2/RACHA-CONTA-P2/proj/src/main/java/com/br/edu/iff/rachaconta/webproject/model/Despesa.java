package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Long pagadorId;

    @Column(nullable = false)
    private Long casaId;

    @Column(nullable = false)
    private LocalDate data;

    public Despesa() {
    }

    public Despesa(
        Long id,
        BigDecimal valorTotal,
        String descricao,
        String tipo,
        Long pagadorId,
        Long casaId,
        LocalDate data
    ) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.tipo = tipo;
        this.pagadorId = pagadorId;
        this.casaId = casaId;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public Long getCasaId() {
        return casaId;
    }

    public void setCasaId(Long casaId) {
        this.casaId = casaId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal calcularValorIndividual(int quantidadeMoradores) {
        if (quantidadeMoradores <= 0) {
            throw new IllegalArgumentException("A casa precisa possuir moradores ativos.");
        }

        return valorTotal.divide(
            BigDecimal.valueOf(quantidadeMoradores),
            2,
            RoundingMode.HALF_UP
        );
    }

    public BigDecimal dividirDespesa(int quantidadeMoradores) {
        return calcularValorIndividual(quantidadeMoradores);
    }
}
