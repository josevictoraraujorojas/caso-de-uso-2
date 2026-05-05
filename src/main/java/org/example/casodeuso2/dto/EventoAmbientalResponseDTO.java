package org.example.casodeuso2.dto;

import org.example.casodeuso2.model.NivelRisco;
import java.util.Date;

public class EventoAmbientalResponseDTO {
    private Long id;
    private String tipo;
    private NivelRisco nivelRisco;
    private Date timestamp;
    private float valorMedio;
    private boolean ativo;
    private VariavelAmbienteResponseDTO variaveisAmbiente;
    private ESP32ResponseDTO esp32;

    public EventoAmbientalResponseDTO() {
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

    public VariavelAmbienteResponseDTO getVariaveisAmbiente() {
        return variaveisAmbiente;
    }

    public void setVariaveisAmbiente(VariavelAmbienteResponseDTO variaveisAmbiente) {
        this.variaveisAmbiente = variaveisAmbiente;
    }

    public ESP32ResponseDTO getEsp32() {
        return esp32;
    }

    public void setEsp32(ESP32ResponseDTO esp32) {
        this.esp32 = esp32;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
