package org.example.casodeuso2.service;

import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.model.Porco;
import org.example.casodeuso2.repository.PorcoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PorcoService {
    private final PorcoRespository repository;

    @Autowired
    public PorcoService(PorcoRespository repository) {
        this.repository = repository;
    }

    // salvar
    public Porco salvar(Porco porco) {
        return repository.save(porco);
    }

    // listar todos
    public List<Porco> listar() {
        return (List<Porco>) repository.findAll();
    }

    // buscar por id
    public Porco buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Porco não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
