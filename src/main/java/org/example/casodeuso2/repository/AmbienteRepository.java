package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.AmbienteData;

import java.util.List;

public interface AmbienteRepository {

    public void salvarSensorData(AmbienteData data);

    public List<AmbienteData> consultarSensoresData();

    public void deletarSensorData(String esp32Id);

}
