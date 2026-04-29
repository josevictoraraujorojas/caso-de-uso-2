package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.SensorCreateDto;
import org.example.casodeuso2.dto.SensorResponseDTO;
import org.example.casodeuso2.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService service;

    @Autowired
    public SensorController(SensorService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public SensorResponseDTO criar(@RequestBody SensorCreateDto sensorCreateDto) {
        return service.salvar(sensorCreateDto);
    }

    //PUT
    @PutMapping("/{sensorId}/variavelAmbiente/{variavelAmbienteId}")
    public SensorResponseDTO adicionarSensor(@PathVariable Long sensorId , @PathVariable Long variavelAmbienteId) {
        return service.adicionarVariavelAmbiente(sensorId, variavelAmbienteId);
    }

    // GET todos
    @GetMapping
    public List<SensorResponseDTO> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public SensorResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @DeleteMapping("/{sensorId}/variavelAmbiente/{variavelAmbienteId}")
    public SensorResponseDTO removerSensor(@PathVariable Long sensorId , @PathVariable Long variavelAmbienteId) {
        return service.removerVariavelAmbiente(sensorId, variavelAmbienteId);
    }
}
