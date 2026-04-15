package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;

@Node
public class EventoAmbiental {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String tipo;
    private NivelRisco nivelRisco;
    private Date timestamp;
    private float valorMedio;
    @Relationship(type = "DERIVA_DE",direction = Relationship.Direction.OUTGOING)
    private ESP32 esp32;
    @Relationship(type = "OCORRE_EM",direction = Relationship.Direction.OUTGOING)
    private Curral curral;


    public EventoAmbiental() {
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

    public NivelRisco getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(float valorMedio) {
        this.valorMedio = valorMedio;
    }

    public ESP32 getEsp32() {
        return esp32;
    }

    public void setEsp32(ESP32 esp32) {
        this.esp32 = esp32;
    }

    public Curral getCurral() {
        return curral;
    }

    public void setCurral(Curral curral) {
        this.curral = curral;
    }
}
