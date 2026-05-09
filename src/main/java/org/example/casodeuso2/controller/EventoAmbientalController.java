package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.EventoAmbientalCreateDTO;
import org.example.casodeuso2.dto.EventoAmbientalResponseDTO;
import org.example.casodeuso2.service.EventoAmbientalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventoAmbiental")
public class EventoAmbientalController {

    private final EventoAmbientalService service;

    @Autowired
    public EventoAmbientalController(
            EventoAmbientalService service) {

        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<EventoAmbientalResponseDTO> criar(
            @RequestBody EventoAmbientalCreateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    // PUT - ADICIONAR ESP32
    @PutMapping("/{eventoAmbienteId}/esp32/{esp32Id}")
    public ResponseEntity<EventoAmbientalResponseDTO> adicionarEsp32(
            @PathVariable Long eventoAmbienteId,
            @PathVariable Long esp32Id) {

        return ResponseEntity.ok(
                service.adicionarEsp32(
                        eventoAmbienteId,
                        esp32Id
                )
        );
    }

    // PUT - ADICIONAR CURRAL
    @PutMapping("/{eventoAmbienteId}/curral/{curralId}")
    public ResponseEntity<EventoAmbientalResponseDTO> adicionarCurral(
            @PathVariable Long eventoAmbienteId,
            @PathVariable Long curralId) {

        return ResponseEntity.ok(
                service.adicionarCurral(
                        eventoAmbienteId,
                        curralId
                )
        );
    }

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<EventoAmbientalResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoAmbientalResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // DELETE - REMOVER ESP32
    @DeleteMapping("/{eventoAmbienteId}/esp32/{esp32Id}")
    public ResponseEntity<EventoAmbientalResponseDTO> removerEsp32(
            @PathVariable Long eventoAmbienteId,
            @PathVariable Long esp32Id) {

        return ResponseEntity.ok(
                service.removerEsp32(
                        eventoAmbienteId,
                        esp32Id
                )
        );
    }

    // DELETE - REMOVER CURRAL
    @DeleteMapping("/{eventoAmbienteId}/curral/{curralId}")
    public ResponseEntity<EventoAmbientalResponseDTO> removerCurral(
            @PathVariable Long eventoAmbienteId,
            @PathVariable Long curralId) {

        return ResponseEntity.ok(
                service.removerCurral(
                        eventoAmbienteId,
                        curralId
                )
        );
    }
}