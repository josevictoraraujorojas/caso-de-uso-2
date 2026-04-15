package org.example.casodeuso2.dto;

import java.util.Set;

public class FazendaResponseDTO {
    private Long id;
    private String nome;
    private String endereco;
    private Set<CurralResponseDTO> currais;

    public FazendaResponseDTO() {
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

    public Set<CurralResponseDTO> getCurrais() {
        return currais;
    }

    public void setCurrais(Set<CurralResponseDTO> currais) {
        this.currais = currais;
    }
}
