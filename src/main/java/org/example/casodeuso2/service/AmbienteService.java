package org.example.casodeuso2.service;

import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.repository.AmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AmbienteService {

    private final AmbienteRepository ambienteRepository;

    @Autowired
    public AmbienteService(AmbienteRepository ambienteRepository) {
        this.ambienteRepository = ambienteRepository;
    }

    // SALVAR
    public void salvarSensorData(AmbienteData data) {

        if (data == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do sensor inválidos"
            );
        }

        AmbienteData sensorData =
                DataMapper.parseObject(data, AmbienteData.class);

        ambienteRepository.salvarSensorData(sensorData);
    }

    // CONSULTAR TODOS
    public List<AmbienteData> consultarSensoresData() {

        List<AmbienteData> dados =
                ambienteRepository.consultarSensoresData();

        if (dados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado ambiental encontrado"
            );
        }

        return dados;
    }

    // CONSULTAR POR ESP32
    public List<AmbienteData> consultarPorEsp32(Long esp32Id) {

        List<AmbienteData> dados =
                ambienteRepository.consultarPorEsp32(esp32Id);

        if (dados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para esse ESP32"
            );
        }

        return dados;
    }

    // CONSULTAR POR VARIAVEL
    public List<AmbienteData> consultarPorVariavel(Long variavelId) {

        List<AmbienteData> dados =
                ambienteRepository.consultarPorVariavel(variavelId);

        if (dados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para essa variável"
            );
        }

        return dados;
    }

    // DELETAR
    public void deletarSensorData(String sensorId) {

        if (sensorId == null || sensorId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Id do sensor inválido"
            );
        }

        ambienteRepository.deletarSensorData(sensorId);
    }
}