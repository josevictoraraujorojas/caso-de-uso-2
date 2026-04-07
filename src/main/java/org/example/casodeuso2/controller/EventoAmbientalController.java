package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.EventoAmbiental;
import org.example.casodeuso2.service.EventoAmbientalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventoAmbiental")
public class EventoAmbientalController {
    private final EventoAmbientalService service;

    @Autowired
    public EventoAmbientalController(EventoAmbientalService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public EventoAmbiental criar(@RequestBody EventoAmbiental eventoAmbiental) {
        return service.salvar(eventoAmbiental);
    }

    // GET todos
    @GetMapping
    public List<EventoAmbiental> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public EventoAmbiental buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

}
