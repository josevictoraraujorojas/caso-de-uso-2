package org.example.casodeuso2.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.io.Serializable;
import java.time.Instant;

@Measurement(name = "medicoes_ambientais")
public class AmbienteData implements Serializable {
    @Column(timestamp = true)
    private Instant time;

    @Column(name = "_value")
    private Double valor;

    @Column(name = "curral_id", tag = true)
    private Long curralId;

    @Column(name = "esp32_id", tag = true)
    private Long esp32Id;

    @Column(name = "fazenda_id", tag = true)
    private Long fazendaId;

    @Column(name = "sensor_id", tag = true)
    private Long sensorId;

    @Column(name = "variavel_id", tag = true)
    private Long variavelId;

    public AmbienteData() {
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getCurralId() {
        return curralId;
    }

    public void setCurralId(Long curralId) {
        this.curralId = curralId;
    }

    public Long getEsp32Id() {
        return esp32Id;
    }

    public void setEsp32Id(Long esp32Id) {
        this.esp32Id = esp32Id;
    }

    public Long getFazendaId() {
        return fazendaId;
    }

    public void setFazendaId(Long fazendaId) {
        this.fazendaId = fazendaId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getVariavelId() {
        return variavelId;
    }

    public void setVariavelId(Long variavelId) {
        this.variavelId = variavelId;
    }
}
