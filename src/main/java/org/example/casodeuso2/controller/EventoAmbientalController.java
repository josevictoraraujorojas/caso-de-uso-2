package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.EventoAmbientalCreateDTO;
import org.example.casodeuso2.dto.EventoAmbientalResponseDTO;
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
    public EventoAmbientalResponseDTO criar(@RequestBody EventoAmbientalCreateDTO eventoAmbientalCreateDTO) {
        return service.salvar(eventoAmbientalCreateDTO);
    }

    //PUT
    @PutMapping("/{eventoAmbienteId}/variavelAmbiente/{variavelAmbienteId}")
    public EventoAmbientalResponseDTO adicionarVariavelAmbiente(@PathVariable Long eventoAmbienteId ,@PathVariable Long variavelAmbienteId) {
        return service.adicionarVaravelAmbiente(eventoAmbienteId,variavelAmbienteId);
    }

    @PutMapping("/{eventoAmbienteId}/curral/{curralId}")
    public EventoAmbientalResponseDTO adicionarCurral(@PathVariable Long eventoAmbienteId ,@PathVariable Long curralId) {
        return service.adicionarCurral(eventoAmbienteId,curralId);
    }

    // GET todos
    @GetMapping
    public List<EventoAmbientalResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public EventoAmbientalResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

}
