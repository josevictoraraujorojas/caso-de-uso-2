package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Porco;
import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository repository;

    @Autowired
    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    // salvar
    public Sensor salvar(Sensor sensor) {
        return repository.save(sensor);
    }

    // listar todos
    public List<Sensor> listar() {
        return (List<Sensor>) repository.findAll();
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
