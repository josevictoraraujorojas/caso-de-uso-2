package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.AmbienteData;

import java.util.List;

public interface AmbienteRepository {

     void salvarSensorData(AmbienteData data);

     List<AmbienteData> consultarSensoresData();

     List<AmbienteData> consultarPorEsp32(Long esp32Id);

     List<AmbienteData> consultarPorVariavel(Long variavelId);

     void deletarSensorData(String esp32Id);

}
