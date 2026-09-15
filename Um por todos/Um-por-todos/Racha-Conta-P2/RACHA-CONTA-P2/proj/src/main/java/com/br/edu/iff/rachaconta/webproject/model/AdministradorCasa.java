package com.br.edu.iff.rachaconta.webproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class AdministradorCasa extends Usuario {

    @Column(nullable = false)
    private String nivelAcesso;

    public AdministradorCasa() {
    }

    public AdministradorCasa(Long id, String nome, String email, String nivelAcesso) {
        super(id, nome, email);
        this.nivelAcesso = nivelAcesso;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
