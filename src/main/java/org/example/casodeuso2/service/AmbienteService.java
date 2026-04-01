package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.AmbienteDataDTO;
import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.repository.AmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AmbienteService {
    private final AmbienteRepository ambienteRepository;

    @Autowired
    public AmbienteService(AmbienteRepository ambienteRepository) {
        this.ambienteRepository = ambienteRepository;
    }

    public void salvarSensorData(AmbienteData data) {
        AmbienteData sensorData = DataMapper.parseObject(data, AmbienteData.class);
        ambienteRepository.salvarSensorData(sensorData);
    }


    public List<AmbienteDataDTO> consultarSensoresData() {
        return DataMapper.parseListObjects(ambienteRepository.consultarSensoresData(), AmbienteDataDTO.class);
    }

    public void deletarSensorData(String sensorId) {
        ambienteRepository.deletarSensorData(sensorId);
    }

    public void processarMensagem(AmbienteData data) {

        if (data.getEsp32Id() == null) {
            System.out.println("Dado inválido: esp32Id null");
            return;
        }
        data.setTimestamp(Instant.now());
        salvarSensorData(data);
    }
}
