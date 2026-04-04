package org.example.casodeuso2.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.example.casodeuso2.config.MQTTProperties;
import org.example.casodeuso2.dto.SensorDTO;
import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.service.AmbienteService;
import org.example.casodeuso2.service.ESP32Service;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.time.Instant;
import java.util.Date;

@Service
public class MQTTListener {

    private final MqttClient cliente;
    private final MQTTProperties properties;
    private final AmbienteService ambienteService;
    private final ESP32Service esp32Service;
    private final ObjectMapper mapper;

    public MQTTListener(MqttClient cliente, MQTTProperties properties, AmbienteService ambienteService, ESP32Service esp32Service, ObjectMapper mapper) {
        this.cliente = cliente;
        this.properties = properties;
        this.ambienteService = ambienteService;
        this.esp32Service = esp32Service;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        iniciarInscricao();
    }

    private void iniciarInscricao() {
        try {

            if (!cliente.isConnected()) {
                cliente.connect();
            }

            cliente.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Conexão perdida: " + throwable.getMessage());
                }

                @Override
                public void messageArrived(String topico, MqttMessage mensagem) {
                    try {
                        String payload = new String(mensagem.getPayload());

                        SensorDTO dto = mapper.readValue(payload, SensorDTO.class);

// salva no Influx
                        AmbienteData ambiente = new AmbienteData();
                        ambiente.setEsp32Id(dto.getEsp32Id());
                        ambiente.setTemperatura(dto.getTemperatura());
                        ambiente.setHumidade(dto.getHumidade());
                        ambiente.setQualidadeAr(dto.getQualidadeAr());
                        ambiente.setTimestamp(Instant.now());

                        ambienteService.salvarSensorData(ambiente);

// salva no Neo4j
                        ESP32 esp = new ESP32();
                        esp.setMacAddress(dto.getEsp32Id());
                        esp.setIp(dto.getIp());
                        esp.setNome(dto.getNome());
                        esp.setDataInstalacao(new Date());

                        esp32Service.salvarOuAtualizar(esp);

                    } catch (Exception e) {
                        System.out.println("Erro ao processar JSON: " + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            cliente.subscribe(properties.getTopic(), properties.getQos());

        } catch (MqttException e) {
            System.out.println("Erro ao iniciar inscrição: " + e.getMessage());
            e.printStackTrace();
        }
    }
}