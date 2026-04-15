package org.example.casodeuso2.mqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.casodeuso2.config.MQTTProperties;
import org.example.casodeuso2.dto.AmbienteDataDTO;
import org.example.casodeuso2.dto.ESP32DTO;
import org.example.casodeuso2.model.*;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.service.AmbienteService;
import org.example.casodeuso2.service.CurralService;
import org.example.casodeuso2.service.ESP32Service;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Optional;

@Service
public class MQTTListener {

    private final MqttClient cliente;
    private final MQTTProperties properties;
    private final AmbienteService ambienteService;
    private final ESP32Service esp32Service;
    private final CurralRepository curralRepository;
    private final ObjectMapper mapper;

    public MQTTListener(MqttClient cliente,
                        MQTTProperties properties,
                        AmbienteService ambienteService,
                        ESP32Service esp32Service, CurralService curralService, CurralRepository curralRepository,
                        ObjectMapper mapper) {

        this.cliente = cliente;
        this.properties = properties;
        this.ambienteService = ambienteService;
        this.esp32Service = esp32Service;
        this.curralRepository = curralRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        iniciar();
    }

    private void iniciar() {
        try {

            if (!cliente.isConnected()) {
                cliente.connect();
            }

            cliente.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Conexão perdida: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topico, MqttMessage mensagem) {

                    String payload = new String(mensagem.getPayload());

                    System.out.println("TOPICO: " + topico);
                    System.out.println("PAYLOAD: " + payload);

                    try {

                        if (topico.equals("ambientedata")) {
                            processarAmbiente(payload);
                        } else if (topico.equals("esp32")) {
                            processarEsp32(payload);
                        }

                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            cliente.subscribe(properties.getTopics().toArray(new String[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processarAmbiente(String payload) throws Exception {


        AmbienteDataDTO dto = mapper.readValue(payload, AmbienteDataDTO.class);
        ESP32 esp32 = esp32Service.buscarPorMac(dto.getEsp32Mac());
        Optional<Curral> curral = Optional.ofNullable(curralRepository.findById(esp32.getCurral().getId()).orElseThrow(() -> new RuntimeException("Curral não encontrado")));
        Sensor sensor = esp32.getSensores()
                .stream()
                .filter(s -> s.getModelo().equalsIgnoreCase(dto.getSensor()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        VariavelAmbiente variavelAmbiente = sensor.getVariaveisAmbientes()
                .stream()
                .filter(v -> v.getNome().equalsIgnoreCase(dto.getCampo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Variável não encontrada"));

        AmbienteData ambiente = new AmbienteData();
        ambiente.setEsp32Id(esp32.getId());
        ambiente.setValor(dto.getValor());
        ambiente.setSensorId(sensor.getId());
        ambiente.setCurralId(esp32.getCurral().getId());
        ambiente.setFazendaId(curral.get().getFazenda().getId());
        ambiente.setVariavelId(variavelAmbiente.getId());

        ambienteService.salvarSensorData(ambiente);
    }

    private void processarEsp32(String payload) throws Exception {

        ESP32DTO dto = mapper.readValue(payload, ESP32DTO.class);

        ESP32 esp = new ESP32();
        esp.setIp(dto.getIp());
        esp.setNome(dto.getNome());
        esp.setMacAddress(dto.getMacAddress());
        esp.setDataInstalacao(new Date());

        esp32Service.salvarOuAtualizar(esp);
    }
}