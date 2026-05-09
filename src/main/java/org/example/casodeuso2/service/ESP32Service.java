package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.ESP32ResponseDTO;
import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.repository.ESP32Repository;
import org.example.casodeuso2.repository.SensorRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ESP32Service {

    private final ESP32Repository repository;
    private final SensorRepository sensorRepository;

    @Autowired
    public ESP32Service(
            ESP32Repository repository,
            SensorRepository sensorRepository) {

        this.repository = repository;
        this.sensorRepository = sensorRepository;
    }

    // SALVAR OU ATUALIZAR
    public void salvarOuAtualizar(ESP32 esp32) {

        if (esp32 == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do ESP32 inválidos"
            );
        }

        ESP32 existente =
                repository.findByMacAddress(esp32.getMacAddress());

        if (existente != null) {

            existente.setIp(esp32.getIp());
            existente.setNome(esp32.getNome());

            repository.save(existente);
            return;
        }

        repository.save(esp32);
    }

    // LISTAR TODOS
    public List<ESP32ResponseDTO> listar() {

        List<ESP32> esp32s = repository.findAll();

        if (esp32s.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum ESP32 encontrado"
            );
        }

        return DataMapper.parseListObjects(
                esp32s,
                ESP32ResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public ESP32ResponseDTO buscarPorId(Long id) {

        ESP32 esp32 = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ESP32 não encontrado"
                ));

        return DataMapper.parseObject(
                esp32,
                ESP32ResponseDTO.class
        );
    }

    // BUSCAR POR MAC
    public ESP32 buscarPorMac(String mac) {

        ESP32 esp32 = repository.findByMacAddress(mac);

        if (esp32 == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ESP32 não encontrado"
            );
        }

        return esp32;
    }

    // BUSCAR POR NOME
    public ESP32ResponseDTO buscarPorNome(String nome) {

        ESP32 esp32 = repository.findByNome(nome);

        if (esp32 == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ESP32 não encontrado"
            );
        }

        return DataMapper.parseObject(
                esp32,
                ESP32ResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        ESP32 esp32 = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ESP32 não encontrado"
                ));

        repository.delete(esp32);
    }

    // REMOVER SENSOR
    public ESP32ResponseDTO removerSensor(
            Long esp32Id,
            Long sensorId) {

        ESP32 esp32 = repository.findById(esp32Id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ESP32 não encontrado"
                ));

        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        if (esp32.getSensores() == null ||
                esp32.getSensores().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse ESP32 não possui sensores"
            );
        }

        if (!esp32.getSensores().contains(sensor)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse sensor não está vinculado a esse ESP32"
            );
        }

        esp32.getSensores().remove(sensor);

        return DataMapper.parseObject(
                repository.save(esp32),
                ESP32ResponseDTO.class
        );
    }
}