package org.example.casodeuso2.dto;

public class AmbienteDataDTO {
    private String esp32Id;
    private double temperatura;
    private double humidade;
    private double qualidadeAr;

    public AmbienteDataDTO() {
    }

    public String getEsp32Id() {
        return esp32Id;
    }

    public void setEsp32Id(String esp32Id) {
        this.esp32Id = esp32Id;
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
