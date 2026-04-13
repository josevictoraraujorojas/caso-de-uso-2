package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.SensorRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class SensorService {
    private final SensorRepository repository;
    private final VariavelAmbienteRepository variavelAmbienteRepository;

    @Autowired
    public SensorService(SensorRepository repository, VariavelAmbienteRepository variavelAmbienteRepository) {
        this.repository = repository;
        this.variavelAmbienteRepository = variavelAmbienteRepository;
    }

    // salvar
    public Sensor salvar(Sensor sensor) {
        return repository.save(sensor);
    }

    public Sensor adicionarVariavelAmbiente(Long sensorId,Long variavelAmbienteId) {
        Sensor sensor = repository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        VariavelAmbiente variavelAmbiente = variavelAmbienteRepository.findById(variavelAmbienteId).orElseThrow(() -> new RuntimeException("Variavel de ambiente não encontrado"));

        if (sensor.getVariaveisAmbientes()==null){
            sensor.setVariaveisAmbientes(new HashSet<>());
        }
        if (sensor.getVariaveisAmbientes().contains(variavelAmbiente)){
            throw new RuntimeException("Essa Variavel de Ambiente já esta vinculada com este Sensor");
        }
        sensor.getVariaveisAmbientes().add(variavelAmbiente);
        return repository.save(sensor);
    }

    // listar todos
    public List<Sensor> listar() {
        return repository.findAll();
    }

    // buscar por id
    public Sensor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
