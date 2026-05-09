package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.LimiteAmbientalCreateDTO;
import org.example.casodeuso2.dto.LimiteAmbientalResponseDTO;
import org.example.casodeuso2.service.LimiteAmbientalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/limiteAmbiental")
public class LimiteAmbientalController {

    private final LimiteAmbientalService service;

    @Autowired
    public LimiteAmbientalController(
            LimiteAmbientalService service) {

        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<LimiteAmbientalResponseDTO> criar(
            @RequestBody LimiteAmbientalCreateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<LimiteAmbientalResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody LimiteAmbientalCreateDTO dto) {

        return ResponseEntity.ok(
                service.editar(id, dto)
        );
    }

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<LimiteAmbientalResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<LimiteAmbientalResponseDTO> buscarPorId(
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
}