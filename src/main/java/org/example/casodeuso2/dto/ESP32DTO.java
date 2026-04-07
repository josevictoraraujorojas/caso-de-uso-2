package org.example.casodeuso2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ESP32DTO {

    private String ip;
    private String nome;

    @JsonProperty("macAddress")
    private String macAddress;

    public ESP32DTO() {}

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
}