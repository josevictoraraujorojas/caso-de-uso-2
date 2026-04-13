package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.Sensor;
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
    public Sensor criar(@RequestBody Sensor sensor) {
        return service.salvar(sensor);
    }

    //PUT
    @PutMapping("/{sensorId}/variavelAmbiente/{variavelAmbienteId}")
    public Sensor adicionarSensor(@PathVariable Long sensorId , @PathVariable Long variavelAmbienteId) {
        return service.adicionarVariavelAmbiente(sensorId, variavelAmbienteId);
    }

    // GET todos
    @GetMapping
    public List<Sensor> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public Sensor buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
