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
import org.example.casodeuso2.repository.EventoAmbientalRepository;
import org.example.casodeuso2.service.AmbienteService;
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
    private final EventoAmbientalRepository eventoAmbientalRepository;
    private final CurralRepository curralRepository;
    private final ObjectMapper mapper;

    public MQTTListener(MqttClient cliente,
                        MQTTProperties properties,
                        AmbienteService ambienteService,
                        ESP32Service esp32Service, EventoAmbientalRepository eventoAmbientalRepository, CurralRepository curralRepository,
                        ObjectMapper mapper) {

        this.cliente = cliente;
        this.properties = properties;
        this.ambienteService = ambienteService;
        this.esp32Service = esp32Service;
        this.eventoAmbientalRepository = eventoAmbientalRepository;
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

    private void processarAmbiente(String payload) {
        try {
            AmbienteDataDTO dto = mapper.readValue(payload, AmbienteDataDTO.class);

            ESP32 esp32 = buscarEsp32(dto);
            Curral curral = buscarCurral(esp32);
            Sensor sensor = buscarSensor(esp32, dto);
            VariavelAmbiente variavel = buscarVariavel(sensor, dto);

            AmbienteData ambiente = criarAmbienteData(dto, esp32, curral, sensor, variavel);

            ambienteService.salvarSensorData(ambiente);

            processarEvento(esp32, curral, variavel, ambiente);

        } catch (Exception e) {
            System.out.println("Erro ao processar ambiente: " + e.getMessage());
        }
    }

    private ESP32 buscarEsp32(AmbienteDataDTO dto) {
        ESP32 esp32 = esp32Service.buscarPorMac(dto.getEsp32Mac());

        if (esp32.getId() == null) {
            throw new RuntimeException("ESP32 sem ID");
        }

        if (esp32.getCurral() == null) {
            throw new RuntimeException("Esp32 nao possui curral");
        }

        return esp32;
    }

    private Curral buscarCurral(ESP32 esp32) {

        if (esp32.getCurral().getId() == null) {
            throw new RuntimeException("Curral sem ID");
        }

        return curralRepository
                .findById(esp32.getCurral().getId())
                .orElseThrow(() -> new RuntimeException("Curral não encontrado"));
    }

    private Sensor buscarSensor(ESP32 esp32, AmbienteDataDTO dto) {
        return esp32.getSensores()
                .stream()
                .filter(s -> s.getModelo().equalsIgnoreCase(dto.getSensor()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    private VariavelAmbiente buscarVariavel(Sensor sensor, AmbienteDataDTO dto) {
        return sensor.getVariaveisAmbientes()
                .stream()
                .filter(v -> v.getNome().equalsIgnoreCase(dto.getCampo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Variável não encontrada"));
    }

    private AmbienteData criarAmbienteData(
            AmbienteDataDTO dto,
            ESP32 esp32,
            Curral curral,
            Sensor sensor,
            VariavelAmbiente variavel
    ) {
        AmbienteData ambiente = new AmbienteData();

        ambiente.setEsp32Id(esp32.getId());
        ambiente.setValor(dto.getValor());
        ambiente.setSensorId(sensor.getId());
        ambiente.setCurralId(curral.getId());
        ambiente.setFazendaId(curral.getFazenda().getId());
        ambiente.setVariavelId(variavel.getId());

        return ambiente;
    }

    private void processarEvento(
            ESP32 esp32,
            Curral curral,
            VariavelAmbiente variavel,
            AmbienteData ambiente
    ) {

        if (variavel.getLimite() == null) return;

        LimiteAmbiental limite = variavel.getLimite();
        double valor = ambiente.getValor();

        String tipoAlto = variavel.getNome() + "_ALTO";
        String tipoBaixo = variavel.getNome() + "_BAIXO";

        if (valor > limite.getValorMax()) {
            criarEventoSeNaoExistir(esp32, curral, tipoAlto, valor);
        }
        else if (valor < limite.getValorMin()) {
            criarEventoSeNaoExistir(esp32, curral, tipoBaixo, valor);
        }
        else {
            fecharEventos(esp32, tipoAlto, tipoBaixo);
        }
    }

    private void criarEventoSeNaoExistir(
            ESP32 esp32,
            Curral curral,
            String tipo,
            double valor
    ) {
        Optional<EventoAmbiental> ativo =
                eventoAmbientalRepository.buscarEventoAtivo(esp32.getId(), tipo);

        if (ativo.isEmpty()) {

            EventoAmbiental evento = new EventoAmbiental();
            evento.setEsp32(esp32);
            evento.setCurral(curral);
            evento.setTipo(tipo);
            evento.setTimestamp(new Date());
            evento.setValorMedio((float) valor);
            evento.setNivelRisco(NivelRisco.ALTO);
            evento.setAtivo(true);

            eventoAmbientalRepository.save(evento);
        }
    }

    private void fecharEventos(ESP32 esp32, String tipoAlto, String tipoBaixo) {

        eventoAmbientalRepository.buscarEventoAtivo(esp32.getId(), tipoAlto)
                .ifPresent(e -> eventoAmbientalRepository.desativarEvento(e.getId()));

        eventoAmbientalRepository.buscarEventoAtivo(esp32.getId(), tipoBaixo)
                .ifPresent(e -> eventoAmbientalRepository.desativarEvento(e.getId()));
    }


    private void processarEsp32(String payload) {

        ESP32DTO dto = mapper.readValue(payload, ESP32DTO.class);

        ESP32 esp = new ESP32();
        esp.setIp(dto.getIp());
        esp.setNome(dto.getNome());
        esp.setMacAddress(dto.getMacAddress());
        esp.setDataInstalacao(new Date());

        esp32Service.salvarOuAtualizar(esp);
    }
}