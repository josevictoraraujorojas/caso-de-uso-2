package org.example.casodeuso2.dto;

public class AmbienteDataDTO {

    private float valor;
    private String campo;
    private String esp32Mac;
    private String sensor;

    public AmbienteDataDTO() {
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getEsp32Mac() {
        return esp32Mac;
    }

    public void setEsp32Mac(String esp32Mac) {
        this.esp32Mac = esp32Mac;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
