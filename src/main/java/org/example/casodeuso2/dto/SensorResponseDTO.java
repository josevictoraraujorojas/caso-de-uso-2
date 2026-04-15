package org.example.casodeuso2.dto;

import java.util.Date;
import java.util.Set;

public class SensorResponseDTO {
    private Long id;
    private String tipo;
    private String modelo;
    private Date dataCalibracao;
    private Set<VariavelAmbienteResponseDTO> variaveisAmbientes;

    public SensorResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<VariavelAmbienteResponseDTO> getVariaveisAmbientes() {
        return variaveisAmbientes;
    }

    public void setVariaveisAmbientes(Set<VariavelAmbienteResponseDTO> variaveisAmbientes) {
        this.variaveisAmbientes = variaveisAmbientes;
    }
}
