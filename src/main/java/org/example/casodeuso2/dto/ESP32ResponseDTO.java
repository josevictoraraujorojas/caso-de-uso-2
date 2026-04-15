package org.example.casodeuso2.dto;

import java.util.Date;
import java.util.Set;

public class ESP32ResponseDTO {
    private Long id;
    private Date dataInstalacao;
    private String ip;
    private String nome;
    private String macAddress;
    private Set<SensorResponseDTO> sensores;

    public ESP32ResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<SensorResponseDTO> getSensores() {
        return sensores;
    }

    public void setSensores(Set<SensorResponseDTO> sensores) {
        this.sensores = sensores;
    }
}
