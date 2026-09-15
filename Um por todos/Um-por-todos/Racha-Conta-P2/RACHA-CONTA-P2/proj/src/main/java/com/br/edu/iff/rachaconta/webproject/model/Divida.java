package com.br.edu.iff.rachaconta.webproject.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dividas")
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private boolean quitada;

    @Column(nullable = false)
    private Long despesaId;

    @Column(nullable = false)
    private Long devedorId;

    @Column(nullable = false)
    private Long credorId;

    public Divida() {
    }

    public Divida(
        Long id,
        BigDecimal valor,
        boolean quitada,
        Long despesaId,
        Long devedorId,
        Long credorId
    ) {
        this.id = id;
        this.valor = valor;
        this.quitada = quitada;
        this.despesaId = despesaId;
        this.devedorId = devedorId;
        this.credorId = credorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }

    public Long getDespesaId() {
        return despesaId;
    }

    public void setDespesaId(Long despesaId) {
        this.despesaId = despesaId;
    }

    public Long getDevedorId() {
        return devedorId;
    }

    public void setDevedorId(Long devedorId) {
        this.devedorId = devedorId;
    }

    public Long getCredorId() {
        return credorId;
    }

    public void setCredorId(Long credorId) {
        this.credorId = credorId;
    }

    public void marcarComoPaga() {
        quitada = true;
    }

    public String verificarStatus() {
        return quitada ? "Paga" : "Pendente";
    }
}
