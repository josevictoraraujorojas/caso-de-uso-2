package org.example.casodeuso2.model;


import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Objects;

@Node
public class VariavelAmbiente {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String nome;
    private String unidade;
    private String descricao;
    @Relationship(type = "TEM_LIMITE", direction = Relationship.Direction.OUTGOING)
    private LimiteAmbiental limite;


    public VariavelAmbiente() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
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

    public LimiteAmbiental getLimite() {
        return limite;
    }

    public void setLimite(LimiteAmbiental limite) {
        this.limite = limite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariavelAmbiente)) return false;
        VariavelAmbiente that = (VariavelAmbiente) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
