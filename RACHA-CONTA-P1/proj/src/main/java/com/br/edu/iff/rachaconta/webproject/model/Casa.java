package com.br.edu.iff.rachaconta.webproject.model;

import java.util.ArrayList;
import java.util.List;

public class Casa {
    private Long id;
    private String nome;
    private String endereco;
    private final List<Long> moradoresIds = new ArrayList<>();
    public Casa() {}
    public Casa(Long id,String nome,String endereco){this.id=id;this.nome=nome;this.endereco=endereco;}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNome(){return nome;} public void setNome(String nome){this.nome=nome;}
    public String getEndereco(){return endereco;} public void setEndereco(String endereco){this.endereco=endereco;}
    public List<Long> getMoradoresIds(){return moradoresIds;}
    public void adicionarMorador(Long moradorId){if(!moradoresIds.contains(moradorId)) moradoresIds.add(moradorId);}
    public void removerMorador(Long moradorId){moradoresIds.remove(moradorId);}
    public List<Long> listarMoradores(){return List.copyOf(moradoresIds);}
}
