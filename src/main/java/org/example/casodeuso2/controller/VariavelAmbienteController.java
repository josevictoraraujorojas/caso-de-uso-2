package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.VariavelAmbienteCreateDTO;
import org.example.casodeuso2.dto.VariavelAmbienteResponseDTO;
import org.example.casodeuso2.service.VariavelAmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variavelAmbiente")
public class VariavelAmbienteController {

    private final VariavelAmbienteService service;

    @Autowired
    public VariavelAmbienteController(
            VariavelAmbienteService service) {

        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<VariavelAmbienteResponseDTO> criar(
            @RequestBody VariavelAmbienteCreateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    // PUT - EDITAR
    @PutMapping("/{id}")
    public ResponseEntity<VariavelAmbienteResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody VariavelAmbienteCreateDTO dto) {

        return ResponseEntity.ok(
                service.editar(id, dto)
        );
    }

    // PUT - ADICIONAR LIMITE AMBIENTAL
    @PutMapping("/{variavelAmbienteId}/limiteAmbiental/{limiteId}")
    public ResponseEntity<VariavelAmbienteResponseDTO> adicionarSensor(
            @PathVariable Long variavelAmbienteId,
            @PathVariable Long limiteId) {

        return ResponseEntity.ok(
                service.adicionarLimiteAmbiental(
                        variavelAmbienteId,
                        limiteId
                )
        );
    }

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<VariavelAmbienteResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<VariavelAmbienteResponseDTO> buscarPorId(
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

    // DELETE - REMOVER LIMITE AMBIENTAL
    @DeleteMapping("/{variavelAmbienteId}/limiteAmbiental/{limiteId}")
    public ResponseEntity<VariavelAmbienteResponseDTO> removerSensor(
            @PathVariable Long variavelAmbienteId,
            @PathVariable Long limiteId) {

        return ResponseEntity.ok(
                service.removerLimiteAmbiental(
                        variavelAmbienteId,
                        limiteId
                )
        );
    }
}