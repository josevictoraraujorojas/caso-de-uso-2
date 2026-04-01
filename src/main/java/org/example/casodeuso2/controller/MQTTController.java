package org.example.casodeuso2.controller;

import org.example.casodeuso2.service.MQTTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MQTTController {

    private final MQTTService service;

    public MQTTController(MQTTService service) {
        this.service = service;
    }

    @GetMapping("/enviar")
    public String enviar(@RequestParam String msg) {
        service.enviar(msg);
        return "Mensagem enviada!";
    }
}
