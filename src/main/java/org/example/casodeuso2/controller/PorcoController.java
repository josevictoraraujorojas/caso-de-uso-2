package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.PorcoCreateDTO;
import org.example.casodeuso2.dto.PorcoResponseDTO;
import org.example.casodeuso2.service.PorcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/porco")
public class PorcoController {
    private final PorcoService service;

    @Autowired
    public PorcoController(PorcoService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<PorcoResponseDTO> criar(@RequestBody PorcoCreateDTO porcoCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(porcoCreateDTO));
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<PorcoResponseDTO> editar(@PathVariable Long id,@RequestBody PorcoCreateDTO porcoCreateDTO) {
        return ResponseEntity.ok(service.editar(id,porcoCreateDTO));
    }

    // GET todos
    @GetMapping
    public ResponseEntity<List<PorcoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<PorcoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
