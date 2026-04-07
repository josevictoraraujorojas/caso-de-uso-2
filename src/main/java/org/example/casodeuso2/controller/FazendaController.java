package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.Fazenda;
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
    public Fazenda criar(@RequestBody Fazenda fazenda) {
        return service.salvar(fazenda);
    }

    // GET todos
    @GetMapping
    public List<Fazenda> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public Fazenda buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
