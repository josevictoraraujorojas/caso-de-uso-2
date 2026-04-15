package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.FazendaCreateDTO;
import org.example.casodeuso2.dto.FazendaResponseDTO;
import org.example.casodeuso2.service.FazendaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public FazendaResponseDTO criar(@RequestBody FazendaCreateDTO fazendaCreateDTO) {
        return service.salvar(fazendaCreateDTO);
    }

    //PUT
    @PutMapping("/{fazendaId}/curral/{curralId}")
    public FazendaResponseDTO adicionarCurral(@PathVariable Long fazendaId, @PathVariable Long curralId) {
        return service.adicionarCurral(fazendaId, curralId);
    }

    // GET todos
    @GetMapping
    public List<FazendaResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public FazendaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @DeleteMapping("/{fazendaId}/curral/{curralId}")
    public FazendaResponseDTO removerCurral(@PathVariable Long fazendaId, @PathVariable Long curralId) {
        return service.removerCurral(fazendaId, curralId);
    }
}
