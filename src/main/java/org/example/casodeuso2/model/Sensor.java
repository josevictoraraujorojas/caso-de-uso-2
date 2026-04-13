package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Node
public class Sensor {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String tipo;
    private String modelo;
    private Date dataCalibracao;
    @Relationship(type = "MEDE",direction = Relationship.Direction.OUTGOING)
    private Set<VariavelAmbiente> variaveisAmbientes;

    public Sensor() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getDataCalibracao() {
        return dataCalibracao;
    }

    public void setDataCalibracao(Date dataCalibracao) {
        this.dataCalibracao = dataCalibracao;
    }

    public Set<VariavelAmbiente> getVariaveisAmbientes() {
        return variaveisAmbientes;
    }

    public void setVariaveisAmbientes(Set<VariavelAmbiente> variaveisAmbientes) {
        this.variaveisAmbientes = variaveisAmbientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return id != null &&  id.equals(sensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
