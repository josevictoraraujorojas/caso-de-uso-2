package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.CurralCreateDTO;
import org.example.casodeuso2.dto.CurralResponseDTO;
import org.example.casodeuso2.service.CurralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curral")
public class CurralController {
    private final CurralService service;

    @Autowired
    public CurralController(CurralService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<CurralResponseDTO> criar(@RequestBody CurralCreateDTO curralCreateDTO) {
        CurralResponseDTO response = service.salvar(curralCreateDTO);
        return ResponseEntity.ok(response);
    }

    // PUT
    @PutMapping("/{curralId}")
    public ResponseEntity<CurralResponseDTO> atualizar(
            @PathVariable Long curralId,
            @RequestBody CurralCreateDTO curralCreateDTO) {

        CurralResponseDTO response = service.editar(curralId, curralCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{curralId}/porco/{porcoId}")
    public ResponseEntity<CurralResponseDTO> adicionarPorco(@PathVariable Long curralId , @PathVariable Long porcoId) {
        CurralResponseDTO response = service.adicionarPorco(curralId, porcoId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{curralId}/esp32/{esp32Id}")
    public ResponseEntity<CurralResponseDTO> adicionarEsp32(@PathVariable Long curralId , @PathVariable Long esp32Id) {
        CurralResponseDTO response = service.adicionarEsp32(curralId, esp32Id);
        return ResponseEntity.ok(response);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<CurralResponseDTO> buscarPorId(@PathVariable Long id) {
        CurralResponseDTO response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // GET todos
    @GetMapping
    public ResponseEntity<List<CurralResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{curralId}/porco/{porcoId}")
    public ResponseEntity<CurralResponseDTO> removerPorco(@PathVariable Long curralId , @PathVariable Long porcoId) {
        return ResponseEntity.ok(
                service.removerPorco(curralId, porcoId)
        );
    }

    @DeleteMapping("/{curralId}/esp32/{esp32Id}")
    public ResponseEntity<CurralResponseDTO> removerEsp32(
            @PathVariable Long curralId,
            @PathVariable Long esp32Id) {

        return ResponseEntity.ok(
                service.removerEsp32(curralId, esp32Id)
        );
    }

}
