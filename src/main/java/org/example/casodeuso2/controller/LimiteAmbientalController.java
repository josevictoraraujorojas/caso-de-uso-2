package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.model.LimiteAmbiental;
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
    public LimiteAmbiental criar(@RequestBody LimiteAmbiental limiteAmbiental) {
        return service.salvar(limiteAmbiental);
    }

    // GET todos
    @GetMapping
    public List<LimiteAmbiental> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public LimiteAmbiental buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
