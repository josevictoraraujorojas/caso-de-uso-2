package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.service.ESP32Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esp32")
public class ESP32Controller {
    private final ESP32Service service;

    @Autowired
    public ESP32Controller(ESP32Service service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public ESP32 criar(@RequestBody ESP32 esp32) {
        return service.salvarOuAtualizar(esp32);
    }

    // GET todos
    @GetMapping
    public List<ESP32> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public ESP32 buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET por nome
    @GetMapping("/nome")
    public List<ESP32> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
