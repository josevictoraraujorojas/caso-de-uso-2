package org.example.casodeuso2.service;

import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LimiteAmbientalService {
    private final LimiteAmbientalRepository repository;

    @Autowired
    public LimiteAmbientalService(LimiteAmbientalRepository repository) {
        this.repository = repository;
    }

    // salvar
    public LimiteAmbiental salvar(LimiteAmbiental limiteAmbiental) {
        return repository.save(limiteAmbiental);
    }

    // listar todos
    public List<LimiteAmbiental> listar() {
        return (List<LimiteAmbiental>) repository.findAll();
    }

    // buscar por id
    public LimiteAmbiental buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Limite ambiental não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
