package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.Set;

@Node
public class Porco {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private int peso;
    private Date nascimento;
    private String raca;
    private String nome;
    private Sexo sexo;
    private int lote;
    @Relationship(type = "COMPOSTO_POR",direction = Relationship.Direction.INCOMING)
    private Curral curral;

    public Porco() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public Curral getCurral() {
        return curral;
    }

    public void setCurral(Curral curral) {
        this.curral = curral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Porco)) return false;
        Porco porco = (Porco) o;
        return id != null && id.equals(porco.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
