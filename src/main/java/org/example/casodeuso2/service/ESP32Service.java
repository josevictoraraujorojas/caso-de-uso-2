package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.ESP32ResponseDTO;
import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.repository.ESP32Repository;
import org.example.casodeuso2.repository.SensorRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ESP32Service {

    private final ESP32Repository repository;
    private final SensorRepository sensorRepository;

    @Autowired
    public ESP32Service(ESP32Repository repository, SensorRepository sensorRepository) {
        this.repository = repository;
        this.sensorRepository = sensorRepository;
    }

    // salvar
    public ESP32 salvarOuAtualizar(ESP32 esp32) {
        ESP32 existente = repository.findByMacAddress(esp32.getMacAddress());

        if (existente != null) {
            existente.setIp(esp32.getIp());
            existente.setNome(esp32.getNome());

            return repository.save(existente);
        }

        return repository.save(esp32);
    }

    public ESP32ResponseDTO adicionarSensor(Long esp32Id, Long sensorId) {
        ESP32 esp32 = repository.findById(esp32Id).orElseThrow(() -> new RuntimeException("Esp32 não encontrado"));
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        if (esp32.getSensores()==null){
            esp32.setSensores(new HashSet<>());
        }
        if (esp32.getSensores().contains(sensor)){
            throw new RuntimeException("Esse sensor já esta vinculado a este esp32");
        }
        esp32.getSensores().add(sensor);
        return DataMapper.parseObject(repository.save(esp32), ESP32ResponseDTO.class);
    }

    // listar todos
    public List<ESP32ResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(), ESP32ResponseDTO.class);
    }

    // buscar por id
    public ESP32ResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("ESP32 não encontrado")), ESP32ResponseDTO.class);
    }

    public ESP32 buscarPorMac(String mac) {
        ESP32 esp32 = repository.findByMacAddress(mac);

        if (esp32 == null) {
            throw new RuntimeException("Esp32 nao encontrado");
        }

        return esp32;
    }

    // buscar por nome
    public ESP32ResponseDTO buscarPorNome(String nome) {
        return DataMapper.parseObject(repository.findByNome(nome),ESP32ResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public ESP32ResponseDTO removerSensor(Long esp32Id, Long sensorId) {
        ESP32 esp32 = repository.findById(esp32Id).orElseThrow(() -> new RuntimeException("Esp32 não encontrado"));
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        if (esp32.getSensores()==null){
            throw new RuntimeException("Esse esp32 não possui sensores");
        }
        if (!esp32.getSensores().contains(sensor)){
            throw new RuntimeException("Esse sensor não está vinculado a esse esp32");
        }
        esp32.getSensores().remove(sensor);
        return DataMapper.parseObject(repository.save(esp32), ESP32ResponseDTO.class);
    }
}