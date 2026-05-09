package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.EventoAmbientalCreateDTO;
import org.example.casodeuso2.dto.EventoAmbientalResponseDTO;
import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.model.EventoAmbiental;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.ESP32Repository;
import org.example.casodeuso2.repository.EventoAmbientalRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class EventoAmbientalService {

    private final EventoAmbientalRepository repository;
    private final CurralRepository curralRepository;
    private final ESP32Repository esp32Repository;

    @Autowired
    public EventoAmbientalService(
            EventoAmbientalRepository repository,
            CurralRepository curralRepository,
            ESP32Repository esp32Repository) {

        this.repository = repository;
        this.curralRepository = curralRepository;
        this.esp32Repository = esp32Repository;
    }

    // SALVAR
    public EventoAmbientalResponseDTO salvar(
            EventoAmbientalCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do evento ambiental inválidos"
            );
        }

        EventoAmbiental evento =
                DataMapper.parseObject(dto, EventoAmbiental.class);

        return DataMapper.parseObject(
                repository.save(evento),
                EventoAmbientalResponseDTO.class
        );
    }

    // ADICIONAR ESP32
    public EventoAmbientalResponseDTO adicionarEsp32(
            Long eventoAmbientalId,
            Long esp32Id) {

        EventoAmbiental eventoAmbiental =
                repository.findById(eventoAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        ESP32 esp32 =
                esp32Repository.findById(esp32Id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "ESP32 não encontrado"
                        ));

        if (eventoAmbiental.getEsp32() != null &&
                Objects.equals(
                        eventoAmbiental.getEsp32().getId(),
                        esp32.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse ESP32 já está vinculado a este evento ambiental"
            );
        }

        eventoAmbiental.setEsp32(esp32);

        return DataMapper.parseObject(
                repository.save(eventoAmbiental),
                EventoAmbientalResponseDTO.class
        );
    }

    // ADICIONAR CURRAL
    public EventoAmbientalResponseDTO adicionarCurral(
            Long eventoAmbientalId,
            Long curralId) {

        EventoAmbiental eventoAmbiental =
                repository.findById(eventoAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        Curral curral =
                curralRepository.findById(curralId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curral não encontrado"
                        ));

        if (eventoAmbiental.getCurral() != null &&
                Objects.equals(
                        eventoAmbiental.getCurral().getId(),
                        curral.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse curral já está vinculado a este evento ambiental"
            );
        }

        eventoAmbiental.setCurral(curral);

        return DataMapper.parseObject(
                repository.save(eventoAmbiental),
                EventoAmbientalResponseDTO.class
        );
    }

    // LISTAR TODOS
    public List<EventoAmbientalResponseDTO> listar() {

        List<EventoAmbiental> eventos = repository.findAll();

        if (eventos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum evento ambiental encontrado"
            );
        }

        return DataMapper.parseListObjects(
                eventos,
                EventoAmbientalResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public EventoAmbientalResponseDTO buscarPorId(Long id) {

        EventoAmbiental evento =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        return DataMapper.parseObject(
                evento,
                EventoAmbientalResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        EventoAmbiental evento =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        repository.delete(evento);
    }

    // REMOVER ESP32
    public EventoAmbientalResponseDTO removerEsp32(
            Long eventoAmbientalId,
            Long esp32Id) {

        EventoAmbiental eventoAmbiental =
                repository.findById(eventoAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        ESP32 esp32 =
                esp32Repository.findById(esp32Id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "ESP32 não encontrado"
                        ));

        if (eventoAmbiental.getEsp32() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse evento ambiental não possui ESP32"
            );
        }

        if (!Objects.equals(
                eventoAmbiental.getEsp32().getId(),
                esp32.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse ESP32 não está vinculado a este evento ambiental"
            );
        }

        eventoAmbiental.setEsp32(null);

        return DataMapper.parseObject(
                repository.save(eventoAmbiental),
                EventoAmbientalResponseDTO.class
        );
    }

    // REMOVER CURRAL
    public EventoAmbientalResponseDTO removerCurral(
            Long eventoAmbientalId,
            Long curralId) {

        EventoAmbiental eventoAmbiental =
                repository.findById(eventoAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento ambiental não encontrado"
                        ));

        Curral curral =
                curralRepository.findById(curralId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curral não encontrado"
                        ));

        if (eventoAmbiental.getCurral() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse evento ambiental não possui curral"
            );
        }

        if (!Objects.equals(
                eventoAmbiental.getCurral().getId(),
                curral.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse curral não está vinculado a este evento ambiental"
            );
        }

        eventoAmbiental.setCurral(null);

        return DataMapper.parseObject(
                repository.save(eventoAmbiental),
                EventoAmbientalResponseDTO.class
        );
    }
}