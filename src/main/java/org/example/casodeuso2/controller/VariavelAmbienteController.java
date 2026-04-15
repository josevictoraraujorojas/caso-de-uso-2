package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.VariavelAmbienteCreateDTO;
import org.example.casodeuso2.dto.VariavelAmbienteResponseDTO;
import org.example.casodeuso2.service.VariavelAmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variavelAmbiente")
public class VariavelAmbienteController {
    private final VariavelAmbienteService service;

    @Autowired
    public VariavelAmbienteController(VariavelAmbienteService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public VariavelAmbienteResponseDTO criar(@RequestBody VariavelAmbienteCreateDTO variavelAmbienteCreateDTO) {
        return service.salvar(variavelAmbienteCreateDTO);
    }

    //PUT
    @PutMapping("/{variavelAmbienteId}/limiteAmbiental/{limiteId}")
    public VariavelAmbienteResponseDTO adicionarSensor(@PathVariable Long variavelAmbienteId, @PathVariable Long limiteId) {
        return service.adicionarLimiteAmbiental(variavelAmbienteId,limiteId);
    }

    // GET todos
    @GetMapping
    public List<VariavelAmbienteResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public VariavelAmbienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
