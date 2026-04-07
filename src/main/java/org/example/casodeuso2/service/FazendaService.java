package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.repository.FazendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FazendaService {
    private final FazendaRepository repository;

    @Autowired
    public FazendaService(FazendaRepository repository) {
        this.repository = repository;
    }

    // salvar
    public Fazenda salvar(Fazenda fazenda) {
        return repository.save(fazenda);
    }

    // listar todos
    public List<Fazenda> listar() {
        return (List<Fazenda>) repository.findAll();
    }

    // buscar por id
    public Fazenda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
