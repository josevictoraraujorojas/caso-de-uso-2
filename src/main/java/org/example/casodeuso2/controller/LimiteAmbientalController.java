package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.LimiteAmbientalCreateDTO;
import org.example.casodeuso2.dto.LimiteAmbientalResponseDTO;
import org.example.casodeuso2.service.LimiteAmbientalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/limiteAmbiental")
public class LimiteAmbientalController {
    private final LimiteAmbientalService service;

    @Autowired
    public LimiteAmbientalController(LimiteAmbientalService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public LimiteAmbientalResponseDTO criar(@RequestBody LimiteAmbientalCreateDTO limiteAmbientalCreateDTO) {
        return service.salvar(limiteAmbientalCreateDTO);
    }

    // GET todos
    @GetMapping
    public List<LimiteAmbientalResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public LimiteAmbientalResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
