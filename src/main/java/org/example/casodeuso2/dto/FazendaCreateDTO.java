package org.example.casodeuso2.dto;

public class FazendaCreateDTO {
    private String nome;
    private String endereco;

    public FazendaCreateDTO() {
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
}
