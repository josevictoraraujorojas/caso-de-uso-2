package org.example.casodeuso2.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfiguracao {
    @Bean
    public MqttClient mqttCliente(MQTTProperties mqttProperties) throws MqttException {
        MqttClient cliente = new MqttClient(mqttProperties.getBroker(), mqttProperties.getClientId());
        MqttConnectOptions opcoes = new MqttConnectOptions();
        opcoes.setCleanSession(true);

        cliente.connect(opcoes);

        return cliente;
    }
}
