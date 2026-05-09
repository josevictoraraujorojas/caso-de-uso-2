package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.FazendaCreateDTO;
import org.example.casodeuso2.dto.FazendaResponseDTO;
import org.example.casodeuso2.service.FazendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fazenda")
public class FazendaController {

    private final FazendaService service;

    @Autowired
    public FazendaController(FazendaService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<FazendaResponseDTO> criar(
            @RequestBody FazendaCreateDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(dto));
    }

    // PUT - EDITAR
    @PutMapping("/{id}")
    public ResponseEntity<FazendaResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody FazendaCreateDTO dto) {

        return ResponseEntity.ok(
                service.editar(id, dto)
        );
    }

    // PUT - ADICIONAR CURRAL
    @PutMapping("/{fazendaId}/curral/{curralId}")
    public ResponseEntity<FazendaResponseDTO> adicionarCurral(
            @PathVariable Long fazendaId,
            @PathVariable Long curralId) {

        return ResponseEntity.ok(
                service.adicionarCurral(
                        fazendaId,
                        curralId
                )
        );
    }

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<FazendaResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<FazendaResponseDTO> buscarPorId(
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

    // DELETE - REMOVER CURRAL
    @DeleteMapping("/{fazendaId}/curral/{curralId}")
    public ResponseEntity<FazendaResponseDTO> removerCurral(
            @PathVariable Long fazendaId,
            @PathVariable Long curralId) {

        return ResponseEntity.ok(
                service.removerCurral(
                        fazendaId,
                        curralId
                )
        );
    }
}