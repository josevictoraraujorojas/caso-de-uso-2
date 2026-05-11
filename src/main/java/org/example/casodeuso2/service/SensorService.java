package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.SensorCreateDto;
import org.example.casodeuso2.dto.SensorResponseDTO;
import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.SensorRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class SensorService {

    private final SensorRepository repository;
    private final VariavelAmbienteRepository variavelAmbienteRepository;

    @Autowired
    public SensorService(
            SensorRepository repository,
            VariavelAmbienteRepository variavelAmbienteRepository) {

        this.repository = repository;
        this.variavelAmbienteRepository = variavelAmbienteRepository;
    }

    // SALVAR
    public SensorResponseDTO salvar(
            SensorCreateDto sensorCreateDto) {

        if (sensorCreateDto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do sensor inválidos"
            );
        }

        Sensor sensor = DataMapper.parseObject(
                sensorCreateDto,
                Sensor.class
        );

        return DataMapper.parseObject(
                repository.save(sensor),
                SensorResponseDTO.class
        );
    }

    // EDITAR
    public SensorResponseDTO editar(
            Long sensorId,
            SensorCreateDto sensorCreateDto) {

        Sensor sensor = repository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        sensor.setTipo(sensorCreateDto.getTipo());
        sensor.setModelo(sensorCreateDto.getModelo());
        sensor.setModelo(sensorCreateDto.getModelo());

        return DataMapper.parseObject(
                repository.save(sensor),
                SensorResponseDTO.class
        );
    }

    // ADICIONAR VARIAVEL AMBIENTE
    public SensorResponseDTO adicionarVariavelAmbiente(
            Long sensorId,
            Long variavelAmbienteId) {

        Sensor sensor = repository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        VariavelAmbiente variavelAmbiente =
                variavelAmbienteRepository.findById(variavelAmbienteId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável de ambiente não encontrada"
                        ));

        if (sensor.getVariaveisAmbientes() == null) {
            sensor.setVariaveisAmbientes(new HashSet<>());
        }

        if (sensor.getVariaveisAmbientes().contains(variavelAmbiente)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Essa variável de ambiente já está vinculada a este sensor"
            );
        }

        sensor.getVariaveisAmbientes().add(variavelAmbiente);

        return DataMapper.parseObject(
                repository.save(sensor),
                SensorResponseDTO.class
        );
    }

    // LISTAR TODOS
    public List<SensorResponseDTO> listar() {

        List<Sensor> sensores = repository.findAll();

        if (sensores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum sensor encontrado"
            );
        }

        return DataMapper.parseListObjects(
                sensores,
                SensorResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public SensorResponseDTO buscarPorId(Long id) {

        Sensor sensor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        return DataMapper.parseObject(
                sensor,
                SensorResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        Sensor sensor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        repository.delete(sensor);
    }

    // REMOVER VARIAVEL AMBIENTE
    public SensorResponseDTO removerVariavelAmbiente(
            Long sensorId,
            Long variavelAmbienteId) {

        Sensor sensor = repository.findById(sensorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sensor não encontrado"
                ));

        VariavelAmbiente variavelAmbiente =
                variavelAmbienteRepository.findById(variavelAmbienteId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável de ambiente não encontrada"
                        ));

        if (sensor.getVariaveisAmbientes() == null ||
                sensor.getVariaveisAmbientes().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse sensor não possui variáveis de ambiente"
            );
        }

        if (!sensor.getVariaveisAmbientes().contains(variavelAmbiente)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Essa variável de ambiente não está vinculada a este sensor"
            );
        }

        sensor.getVariaveisAmbientes().remove(variavelAmbiente);

        return DataMapper.parseObject(
                repository.save(sensor),
                SensorResponseDTO.class
        );
    }
}