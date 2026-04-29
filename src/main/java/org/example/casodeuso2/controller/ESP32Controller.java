package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.ESP32ResponseDTO;
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

    //PUT
    @PutMapping("/{esp32Id}/sensor/{sensorId}")
    public ESP32ResponseDTO adicionarSensor(@PathVariable Long esp32Id , @PathVariable Long sensorId) {
        return service.adicionarSensor(esp32Id,sensorId);
    }

    // GET todos
    @GetMapping
    public List<ESP32ResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public ESP32ResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET por nome
    @GetMapping("/nome")
    public ESP32ResponseDTO buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @DeleteMapping("/{esp32Id}/sensor/{sensorId}")
    public ESP32ResponseDTO removerSensor(@PathVariable Long esp32Id , @PathVariable Long sensorId) {
        return service.removerSensor(esp32Id,sensorId);
    }
}
