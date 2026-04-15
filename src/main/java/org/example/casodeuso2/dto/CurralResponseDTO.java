package org.example.casodeuso2.dto;

import java.util.Set;

public class CurralResponseDTO {

    private Long id;
    private int capacidade;
    private String nome;
    private Set<PorcoResponseDTO> porcos;
    private Set<ESP32ResponseDTO> esp32;

    public CurralResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<PorcoResponseDTO> getPorcos() {
        return porcos;
    }

    public void setPorcos(Set<PorcoResponseDTO> porcos) {
        this.porcos = porcos;
    }

    public Set<ESP32ResponseDTO> getEsp32() {
        return esp32;
    }

    public void setEsp32(Set<ESP32ResponseDTO> esp32) {
        this.esp32 = esp32;
    }
}
