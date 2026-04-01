package org.example.casodeuso2.controller;

import org.example.casodeuso2.mqtt.MQTTListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MQTTController {

    private final MQTTListener service;

    public MQTTController(MQTTListener service) {
        this.service = service;
    }

    @GetMapping("/enviar")
    public String enviar(@RequestParam String msg) {
        service.enviar(msg);
        return "Mensagem enviada!";
    }
}
