package com.br.edu.iff.rachaconta.webproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "moradores")
public class Morador extends Usuario {

    @Column(nullable = false)
    private boolean ativo = true;

    public Morador() {
    }

    public Morador(Long id, String nome, String email, boolean ativo) {
        super(id, nome, email);
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void acompanharPagamento() {
        // Regra reservada para evolução do domínio.
    }

    public void quitarDivida() {
        // Regra reservada para evolução do domínio.
    }
}
