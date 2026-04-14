package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Curral {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private int capacidade;
    private String nome;
    @Relationship(type = "COMPOSTO_POR",direction = Relationship.Direction.OUTGOING)
    private Set<Porco> porcos;
    @Relationship(type = "POSSUI_ESP",direction = Relationship.Direction.OUTGOING)
    private Set<ESP32> esp32;
    @Relationship(type = "POSSUI_CURRAL",direction = Relationship.Direction.INCOMING)
    private Fazenda fazenda;

    public Curral() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Porco> getPorcos() {
        return porcos;
    }

    public void setPorcos(Set<Porco> porcos) {
        this.porcos = porcos;
    }

    public Set<ESP32> getEsp32() {
        return esp32;
    }

    public void setEsp32(Set<ESP32> esp32) {
        this.esp32 = esp32;
    }

    public Fazenda getFazenda() {
        return fazenda;
    }

    public void setFazenda(Fazenda fazenda) {
        this.fazenda = fazenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curral)) return false;
        Curral curral = (Curral) o;
        return id != null && id.equals(curral.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
