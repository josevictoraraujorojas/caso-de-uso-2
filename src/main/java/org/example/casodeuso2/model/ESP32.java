package org.example.casodeuso2.model;

import jakarta.annotation.Nullable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

@Node
public class ESP32 {

    @Id @GeneratedValue
    private @Nullable Long id;
    private Date dataInstalacao;
    private String ip;
    private String nome;
    private String macAddress;
    @Relationship(type = "COLETA_DADOS_DE", direction = Relationship.Direction.OUTGOING)
    private Set<Sensor> sensores;

    public ESP32() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESP32 esp32 = (ESP32) o;
        return id != null && id.equals(esp32.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public Date getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(Date dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Set<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(Set<Sensor> sensores) {
        this.sensores = sensores;
    }
}

