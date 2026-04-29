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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EventoAmbientalService {
    private final EventoAmbientalRepository repository;
    private final CurralRepository curralRepository;
    private final ESP32Repository esp32Repository;

    @Autowired
    public EventoAmbientalService(EventoAmbientalRepository repository, CurralRepository curralRepository, ESP32Repository esp32Repository) {
        this.repository = repository;
        this.curralRepository = curralRepository;
        this.esp32Repository = esp32Repository;
    }

    // salvar
    public EventoAmbientalResponseDTO salvar(EventoAmbientalCreateDTO eventoAmbientalCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(eventoAmbientalCreateDTO,EventoAmbiental.class)),EventoAmbientalResponseDTO.class);
    }


    public EventoAmbientalResponseDTO adicionarEsp32(Long eventoAmbientalId, Long esp32Id){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        ESP32 esp32 = esp32Repository.findById(esp32Id).orElseThrow(() -> new RuntimeException("ESP32 não encontrado"));

        if (eventoAmbiental.getEsp32()==null){
            eventoAmbiental.setEsp32(esp32);
            return DataMapper.parseObject(repository.save(eventoAmbiental), EventoAmbientalResponseDTO.class);
        }
        if (Objects.equals(eventoAmbiental.getEsp32().getId(), esp32.getId())) {
            throw new RuntimeException("Esse esp32 já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setEsp32(esp32);
        return DataMapper.parseObject(repository.save(eventoAmbiental), EventoAmbientalResponseDTO.class);
    }

    public EventoAmbientalResponseDTO adicionarCurral(Long eventoAmbientalId, Long curralId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        Curral curral = curralRepository.findById(curralId).orElseThrow(() -> new RuntimeException("Curral não encontrado"));

        if (eventoAmbiental.getCurral()==null){
            eventoAmbiental.setCurral(curral);
            return DataMapper.parseObject(repository.save(eventoAmbiental),EventoAmbientalResponseDTO.class);
        }

        if (Objects.equals(eventoAmbiental.getCurral().getId(), curral.getId())) {
            throw new RuntimeException("Esse curral já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setCurral(curral);
        return DataMapper.parseObject(repository.save(eventoAmbiental),EventoAmbientalResponseDTO.class);
    }

    // listar todos
    public List<EventoAmbientalResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(), EventoAmbientalResponseDTO.class);
    }

    // buscar por id
    public EventoAmbientalResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Evento ambiental não encontrado")), EventoAmbientalResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public EventoAmbientalResponseDTO removerEsp32(Long eventoAmbientalId, Long esp32Id){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        ESP32 esp32 = esp32Repository.findById(esp32Id).orElseThrow(() -> new RuntimeException("ESP32 não encontrado"));

        if (eventoAmbiental.getEsp32()==null){
            throw new RuntimeException("Esse Evento ambiental não possui esp32");
        }
        if (!Objects.equals(eventoAmbiental.getEsp32().getId(), esp32.getId())) {
            throw new RuntimeException("Esse esp32 não esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setEsp32(null);
        return DataMapper.parseObject(repository.save(eventoAmbiental), EventoAmbientalResponseDTO.class);
    }

    public EventoAmbientalResponseDTO removerCurral(Long eventoAmbientalId, Long curralId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        Curral curral = curralRepository.findById(curralId).orElseThrow(() -> new RuntimeException("Curral não encontrado"));

        if (eventoAmbiental.getCurral()==null){
            throw new RuntimeException("Esse Evento ambiental não possui curral");
        }

        if (!Objects.equals(eventoAmbiental.getCurral().getId(), curral.getId())) {
            throw new RuntimeException("Esse curral não esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setCurral(null);
        return DataMapper.parseObject(repository.save(eventoAmbiental),EventoAmbientalResponseDTO.class);
    }
}
