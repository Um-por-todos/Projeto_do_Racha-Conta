package com.br.edu.iff.rachaconta.webproject.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "casas")
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @ElementCollection
    @CollectionTable(
        name = "casa_moradores",
        joinColumns = @JoinColumn(name = "casa_id", nullable = false)
    )
    @Column(name = "morador_id", nullable = false)
    private List<Long> moradoresIds = new ArrayList<>();

    public Casa() {
    }

    public Casa(Long id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Long> getMoradoresIds() {
        return moradoresIds;
    }

    public void adicionarMorador(Long moradorId) {
        if (moradorId != null && !moradoresIds.contains(moradorId)) {
            moradoresIds.add(moradorId);
        }
    }

    public void removerMorador(Long moradorId) {
        moradoresIds.remove(moradorId);
    }

    public List<Long> listarMoradores() {
        return List.copyOf(moradoresIds);
    }
}
