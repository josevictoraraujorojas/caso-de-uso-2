package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.PorcoCreateDTO;
import org.example.casodeuso2.dto.PorcoResponseDTO;
import org.example.casodeuso2.service.PorcoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PorcoResponseDTO criar(@RequestBody PorcoCreateDTO porcoCreateDTO) {
        return service.salvar(porcoCreateDTO);
    }

    // GET todos
    @GetMapping
    public List<PorcoResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public PorcoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
