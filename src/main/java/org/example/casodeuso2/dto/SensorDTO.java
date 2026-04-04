package org.example.casodeuso2.dto;

public class SensorDTO {

    private String esp32Id;
    private String ip;
    private String nome;

    private double temperatura;
    private double humidade;
    private double qualidadeAr;

    public SensorDTO() {
    }

    public String getEsp32Id() {
        return esp32Id;
    }

    public void setEsp32Id(String esp32Id) {
        this.esp32Id = esp32Id;
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

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumidade() {
        return humidade;
    }

    public void setHumidade(double humidade) {
        this.humidade = humidade;
    }

    public double getQualidadeAr() {
        return qualidadeAr;
    }

    public void setQualidadeAr(double qualidadeAr) {
        this.qualidadeAr = qualidadeAr;
    }
}
