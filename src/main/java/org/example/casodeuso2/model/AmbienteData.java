package org.example.casodeuso2.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "medicoes_ambientais")
public class AmbienteData {
    @Column(tag = true,name = "esp32_id")
    private String esp32Id;
    @Column
    private double temperatura;
    @Column
    private double humidade;
    @Column(name = "qualidade_ar")
    private double qualidadeAr;
    @Column(timestamp = true)
    private Instant timestamp;

    public AmbienteData() {
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getEsp32Id() {
        return esp32Id;
    }

    public void setEsp32Id(String esp32Id) {
        this.esp32Id = esp32Id;
    }
}
