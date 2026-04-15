package org.example.casodeuso2.dto;

public class VariavelAmbienteResponseDTO {

    private Long id;
    private String nome;
    private String unidade;
    private String descricao;
    private LimiteAmbientalResponseDTO limite;

    public VariavelAmbienteResponseDTO() {
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LimiteAmbientalResponseDTO getLimite() {
        return limite;
    }

    public void setLimite(LimiteAmbientalResponseDTO limite) {
        this.limite = limite;
    }
}
