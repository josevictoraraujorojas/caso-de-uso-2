package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.service.VariavelAmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variavelAmbiente")
public class VariavelAmbienteController {
    private VariavelAmbienteService service;

    @Autowired
    public VariavelAmbienteController(VariavelAmbienteService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public VariavelAmbiente criar(@RequestBody VariavelAmbiente variavelAmbiente) {
        return service.salvar(variavelAmbiente);
    }

    // GET todos
    @GetMapping
    public List<VariavelAmbiente> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public VariavelAmbiente buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
