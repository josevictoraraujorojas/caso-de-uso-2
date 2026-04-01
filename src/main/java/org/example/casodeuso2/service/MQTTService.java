package org.example.casodeuso2.service;

import org.eclipse.paho.client.mqttv3.*;
import org.example.casodeuso2.config.MQTTProperties;
import org.springframework.stereotype.Service;

@Service
public class MQTTService {
    private final MqttClient cliente;
    private final MQTTProperties properties;

    public MQTTService(MqttClient cliente, MQTTProperties properties) {
        this.cliente = cliente;
        this.properties = properties;

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
                public void messageArrived(String topico, MqttMessage mensagem) throws Exception {
                    System.out.println("-----------Chegada de mensagem-----------");
                    System.out.println("Topico: " + topico);
                    System.out.println("Qualidade de Serviço: " + mensagem.getQos());
                    System.out.println("Mensagem: " + mensagem.toString());
                    System.out.println("-----------------------------------------");
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

