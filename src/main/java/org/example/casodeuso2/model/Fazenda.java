package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Fazenda {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String nome;
    private String endereco;
    @Relationship(type = "POSSUI_CURRAL",direction = Relationship.Direction.OUTGOING)
    private Set<Curral> currais;

    public Fazenda() {
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Set<Curral> getCurrais() {
        return currais;
    }

    public void setCurrais(Set<Curral> currais) {
        this.currais = currais;
    }
}
