package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.AmbienteData;

import java.util.List;

public interface AmbienteRepository {

    public void salvarSensorData(AmbienteData data);

    public List<AmbienteData> consultarSensoresData();

    public List<AmbienteData> consultarPorEsp32(Long esp32Id);

    public List<AmbienteData> consultarPorVariavel(Long variavelId);

    public void deletarSensorData(String esp32Id);

}
