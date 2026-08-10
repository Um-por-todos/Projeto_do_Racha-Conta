package com.br.edu.iff.rachaconta.webproject.model;

public class AdministradorCasa extends Usuario {
    private String nivelAcesso;

    public AdministradorCasa() {}

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
