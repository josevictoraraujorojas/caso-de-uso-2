package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.SensorCreateDto;
import org.example.casodeuso2.dto.SensorResponseDTO;
import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.SensorRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
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
    public SensorResponseDTO salvar(SensorCreateDto sensorCreateDto) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(sensorCreateDto,Sensor.class)),SensorResponseDTO.class);
    }

    public SensorResponseDTO adicionarVariavelAmbiente(Long sensorId,Long variavelAmbienteId) {
        Sensor sensor = repository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        VariavelAmbiente variavelAmbiente = variavelAmbienteRepository.findById(variavelAmbienteId).orElseThrow(() -> new RuntimeException("Variavel de ambiente não encontrado"));

        if (sensor.getVariaveisAmbientes()==null){
            sensor.setVariaveisAmbientes(new HashSet<>());
        }
        if (sensor.getVariaveisAmbientes().contains(variavelAmbiente)){
            throw new RuntimeException("Essa Variavel de Ambiente já esta vinculada com este Sensor");
        }
        sensor.getVariaveisAmbientes().add(variavelAmbiente);
        return DataMapper.parseObject(repository.save(sensor),SensorResponseDTO.class);
    }

    // listar todos
    public List<SensorResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(),SensorResponseDTO.class);
    }

    // buscar por id
    public SensorResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Sensor não encontrado")),SensorResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public SensorResponseDTO removerVariavelAmbiente(Long sensorId,Long variavelAmbienteId) {
        Sensor sensor = repository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        VariavelAmbiente variavelAmbiente = variavelAmbienteRepository.findById(variavelAmbienteId).orElseThrow(() -> new RuntimeException("Variavel de ambiente não encontrado"));

        if (sensor.getVariaveisAmbientes()==null){
            throw new RuntimeException("Esse Sensor ambiental não possui Variavel de ambiente");
        }
        if (!sensor.getVariaveisAmbientes().contains(variavelAmbiente)){
            throw new RuntimeException("Essa Variavel de Ambiente não esta vinculada com este Sensor");
        }
        sensor.getVariaveisAmbientes().remove(variavelAmbiente);
        return DataMapper.parseObject(repository.save(sensor),SensorResponseDTO.class);
    }
}
