package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.AmbienteDataDTO;
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
    public List<AmbienteDataDTO> consultarSensores() {
        return ambienteService.consultarSensoresData();
    }

    @DeleteMapping("/{esp32Id}")
    public void deleteSensor(@PathVariable String esp32Id) {
        ambienteService.deletarSensorData(esp32Id);
    }
}
