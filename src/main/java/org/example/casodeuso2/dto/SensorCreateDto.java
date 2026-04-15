package org.example.casodeuso2.dto;

import java.util.Date;

public class SensorCreateDto {
    private String tipo;
    private String modelo;
    private Date dataCalibracao;

    public SensorCreateDto() {
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
}
