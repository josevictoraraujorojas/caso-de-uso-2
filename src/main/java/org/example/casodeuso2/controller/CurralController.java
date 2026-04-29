package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.CurralCreateDTO;
import org.example.casodeuso2.dto.CurralResponseDTO;
import org.example.casodeuso2.service.CurralService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CurralResponseDTO criar(@RequestBody CurralCreateDTO curralCreateDTO) {
        return service.salvar(curralCreateDTO);
    }

    //PUT
    @PutMapping("/{curralId}/porco/{porcoId}")
    public CurralResponseDTO adicionarPorco(@PathVariable Long curralId , @PathVariable Long porcoId) {
        return service.adicionarPorco(curralId, porcoId);
    }

    @PutMapping("/{curralId}/esp32/{esp32Id}")
    public CurralResponseDTO adicionarEsp32(@PathVariable Long curralId , @PathVariable Long esp32Id) {
        return service.adicionarEsp32(curralId, esp32Id);
    }

    // GET por ID
    @GetMapping("/{id}")
    public CurralResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET todos
    @GetMapping
    public List<CurralResponseDTO> listar() {
        return service.listar();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @DeleteMapping("/{curralId}/porco/{porcoId}")
    public CurralResponseDTO removerPorco(@PathVariable Long curralId , @PathVariable Long porcoId) {
        return service.removerPorco(curralId, porcoId);
    }

    @DeleteMapping("/{curralId}/esp32/{esp32Id}")
    public CurralResponseDTO removerEsp32(@PathVariable Long curralId , @PathVariable Long esp32Id) {
        return service.removerEsp32(curralId, esp32Id);
    }

}
