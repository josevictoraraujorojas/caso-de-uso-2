package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.ESP32ResponseDTO;
import org.example.casodeuso2.service.ESP32Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<ESP32ResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ESP32ResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // GET POR NOME
    @GetMapping("/nome")
    public ResponseEntity<ESP32ResponseDTO> buscarPorNome(
            @RequestParam String nome) {

        return ResponseEntity.ok(
                service.buscarPorNome(nome)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // REMOVER SENSOR
    @DeleteMapping("/{esp32Id}/sensor/{sensorId}")
    public ResponseEntity<ESP32ResponseDTO> removerSensor(
            @PathVariable Long esp32Id,
            @PathVariable Long sensorId) {

        return ResponseEntity.ok(
                service.removerSensor(esp32Id, sensorId)
        );
    }
}