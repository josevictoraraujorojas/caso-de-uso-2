package org.example.casodeuso2.service;

import org.example.casodeuso2.model.EventoAmbiental;
import org.example.casodeuso2.repository.EventoAmbientalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoAmbientalService {
    private final EventoAmbientalRepository repository;

    @Autowired
    public EventoAmbientalService(EventoAmbientalRepository repository) {
        this.repository = repository;
    }

    // salvar
    public EventoAmbiental salvar(EventoAmbiental eventoAmbiental) {
        return repository.save(eventoAmbiental);
    }

    // listar todos
    public List<EventoAmbiental> listar() {
        return (List<EventoAmbiental>) repository.findAll();
    }

    // buscar por id
    public EventoAmbiental buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento ambiental não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
