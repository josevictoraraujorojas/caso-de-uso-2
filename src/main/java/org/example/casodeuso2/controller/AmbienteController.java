package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambiente")
public class AmbienteController {

    private final AmbienteService ambienteService;

    @Autowired
    public AmbienteController(AmbienteService ambienteService) {
        this.ambienteService = ambienteService;
    }

    @GetMapping
    public ResponseEntity<List<AmbienteData>> consultarSensores() {

        return ResponseEntity.ok(
                ambienteService.consultarSensoresData()
        );
    }

    @GetMapping("/esp32/{esp32Id}")
    public ResponseEntity<List<AmbienteData>> consultarPorEsp32(
            @PathVariable Long esp32Id) {

        return ResponseEntity.ok(
                ambienteService.consultarPorEsp32(esp32Id)
        );
    }

    @GetMapping("/variavel/{variavelId}")
    public ResponseEntity<List<AmbienteData>> consultarPorVariavel(
            @PathVariable Long variavelId) {

        return ResponseEntity.ok(
                ambienteService.consultarPorVariavel(variavelId)
        );
    }

    @DeleteMapping("/{esp32Id}")
    public ResponseEntity<Void> deleteSensor(
            @PathVariable String esp32Id) {

        ambienteService.deletarSensorData(esp32Id);

        return ResponseEntity.noContent().build();
    }
}