package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveSensor(@RequestBody AmbienteData data) {
        ambienteService.salvarSensorData(data);
    }

    @GetMapping
    public List<AmbienteData> consultarSensores() {
        return ambienteService.consultarSensoresData();
    }

    @GetMapping("/esp32/{esp32Id}")
    public List<AmbienteData> consultarPorEsp32(@PathVariable Long esp32Id) {
        return ambienteService.consultarPorEsp32(esp32Id);
    }
    @GetMapping("/variavel/{variavelId}")
    public List<AmbienteData> consultarPorVariavel(@PathVariable Long variavelId) {
        return ambienteService.consultarPorVariavel(variavelId);
    }

    @DeleteMapping("/{esp32Id}")
    public void deleteSensor(@PathVariable String esp32Id) {
        ambienteService.deletarSensorData(esp32Id);
    }
}
