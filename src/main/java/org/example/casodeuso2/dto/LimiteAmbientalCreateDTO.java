package org.example.casodeuso2.dto;

public class LimiteAmbientalCreateDTO {
    private float valorMin;
    private float valorMax;

    public LimiteAmbientalCreateDTO() {
    }

    public float getValorMin() {
        return valorMin;
    }

    public void setValorMin(float valorMin) {
        this.valorMin = valorMin;
    }

    public float getValorMax() {
        return valorMax;
    }

    public void setValorMax(float valorMax) {
        this.valorMax = valorMax;
    }
}
