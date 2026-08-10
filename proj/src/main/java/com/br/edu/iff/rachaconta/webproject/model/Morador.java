package com.br.edu.iff.rachaconta.webproject.model;

public class Morador extends Usuario {
    private boolean ativo = true;
    public Morador() {}
    public Morador(Long id,String nome,String email,boolean ativo){super(id,nome,email);this.ativo=ativo;}
    public boolean isAtivo(){return ativo;} public void setAtivo(boolean ativo){this.ativo=ativo;}
    public void acompanharPagamento() {}
    public void quitarDivida() {}
}
