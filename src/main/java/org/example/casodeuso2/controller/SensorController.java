package org.example.casodeuso2.controller;

import org.example.casodeuso2.dto.SensorCreateDto;
import org.example.casodeuso2.dto.SensorResponseDTO;
import org.example.casodeuso2.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SensorResponseDTO> criar(
            @RequestBody SensorCreateDto sensorCreateDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(sensorCreateDto));
    }

    // PUT - EDITAR
    @PutMapping("/{id}")
    public ResponseEntity<SensorResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody SensorCreateDto sensorCreateDto) {

        return ResponseEntity.ok(
                service.editar(id, sensorCreateDto)
        );
    }

    // PUT - ADICIONAR VARIAVEL AMBIENTE
    @PutMapping("/{sensorId}/variavelAmbiente/{variavelAmbienteId}")
    public ResponseEntity<SensorResponseDTO> adicionarSensor(
            @PathVariable Long sensorId,
            @PathVariable Long variavelAmbienteId) {

        return ResponseEntity.ok(
                service.adicionarVariavelAmbiente(
                        sensorId,
                        variavelAmbienteId
                )
        );
    }

    // GET TODOS
    @GetMapping
    public ResponseEntity<List<SensorResponseDTO>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<SensorResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // DELETE - REMOVER VARIAVEL AMBIENTE
    @DeleteMapping("/{sensorId}/variavelAmbiente/{variavelAmbienteId}")
    public ResponseEntity<SensorResponseDTO> removerSensor(
            @PathVariable Long sensorId,
            @PathVariable Long variavelAmbienteId) {

        return ResponseEntity.ok(
                service.removerVariavelAmbiente(
                        sensorId,
                        variavelAmbienteId
                )
        );
    }
}