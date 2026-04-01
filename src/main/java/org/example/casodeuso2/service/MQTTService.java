package org.example.casodeuso2.service;

import org.eclipse.paho.client.mqttv3.*;
import org.example.casodeuso2.config.MQTTProperties;
import org.example.casodeuso2.model.AmbienteData;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class MQTTService {
    private final MqttClient cliente;
    private final MQTTProperties properties;

    private final AmbienteService ambienteService;

    public MQTTService(MqttClient cliente, MQTTProperties properties, AmbienteService ambienteService) {
        this.cliente = cliente;
        this.properties = properties;
        this.ambienteService = ambienteService;

        iniciarInscricao();
    }

    private void iniciarInscricao(){
        try {
            cliente.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Conexão perdida: " + throwable.getMessage());
                }

                @Override
                public void messageArrived(String topico, MqttMessage mensagem) {
                    try {
                        String payload = mensagem.toString();

                        ObjectMapper mapper = new ObjectMapper();
                        AmbienteData data = mapper.readValue(payload, AmbienteData.class);

                        ambienteService.salvarSensorData(data);

                    } catch (Exception e) {
                        System.out.println("Erro ao processar JSON: " + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("Entrega completa: " + iMqttDeliveryToken.isComplete());
                }
            });

            cliente.subscribe(properties.getTopic(),properties.getQos());
        } catch (MqttException e) {
            System.out.println("Erro ao iniciar Inscricao");
        }
    }

    public void enviar(String mensagem){
        MqttMessage message = new MqttMessage(mensagem.getBytes());
        message.setQos(properties.getQos());

        try {
            cliente.publish(properties.getTopic(),message);
        } catch (MqttException e) {
            System.out.println("Erro ao enviar a menssagem: " + e.getMessage());
        }
    }
}

