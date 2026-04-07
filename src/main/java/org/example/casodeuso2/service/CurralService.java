package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.repository.CurralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurralService {
    private final CurralRepository repository;

    @Autowired
    public CurralService(CurralRepository repository) {
        this.repository = repository;
    }

    // salvar
    public Curral salvar(Curral curral) {
        return repository.save(curral);
    }

    // listar todos
    public List<Curral> listar() {
        return (List<Curral>) repository.findAll();
    }

    // buscar por id
    public Curral buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curral não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
