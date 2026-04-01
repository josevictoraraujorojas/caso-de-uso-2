package org.example.casodeuso2.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.example.casodeuso2.config.MQTTProperties;
import org.example.casodeuso2.model.AmbienteData;
import org.example.casodeuso2.service.AmbienteService;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Service
public class MQTTListener {

    private final MqttClient cliente;
    private final MQTTProperties properties;
    private final AmbienteService ambienteService;
    private final ObjectMapper mapper = new ObjectMapper();

    public MQTTListener(MqttClient cliente, MQTTProperties properties, AmbienteService ambienteService) {
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

                        AmbienteData data = mapper.readValue(payload, AmbienteData.class);
                        ambienteService.processarMensagem(data);

                    } catch (Exception e) {
                        System.out.println("Erro ao processar JSON: " + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

            cliente.subscribe(properties.getTopic(), properties.getQos());

        } catch (MqttException e) {
            System.out.println("Erro ao iniciar Inscricao");
        }
    }
}