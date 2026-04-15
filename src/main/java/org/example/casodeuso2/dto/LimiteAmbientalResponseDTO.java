package org.example.casodeuso2.dto;

public class LimiteAmbientalResponseDTO {
    private Long id;
    private float valorMin;
    private float valorMax;

    public LimiteAmbientalResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
