package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.CurralCreateDTO;
import org.example.casodeuso2.dto.CurralResponseDTO;
import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.model.Porco;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.ESP32Repository;
import org.example.casodeuso2.repository.PorcoRespository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class CurralService {
    private final CurralRepository repository;
    private final PorcoRespository porcoRespository;
    private final ESP32Repository esp32Repository;

    @Autowired
    public CurralService(CurralRepository repository, PorcoRespository porcoRespository, ESP32Repository esp32Repository) {
        this.repository = repository;
        this.porcoRespository = porcoRespository;
        this.esp32Repository = esp32Repository;
    }

    public CurralResponseDTO salvar(CurralCreateDTO curralCreateDTO) {
        Curral curral = DataMapper.parseObject(curralCreateDTO, Curral.class);
        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    public CurralResponseDTO editar(Long curralId, CurralCreateDTO curralCreateDTO) {

        Curral curral = repository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        curral.setNome(curralCreateDTO.getNome());
        curral.setCapacidade(curralCreateDTO.getCapacidade());

        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    public CurralResponseDTO adicionarPorco(Long curralId, Long porcoId){
        Curral curral = repository.findById(curralId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Curral não encontrado"
        ));
        Porco porco = porcoRespository.findById(porcoId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Porco não encontrado"
        ));

        if (curral.getPorcos()==null){
            curral.setPorcos(new HashSet<>());
        }
        if (curral.getPorcos().contains(porco)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse porco já está vinculado a este curral"
            );
        }
        if (curral.getPorcos().size() >= curral.getCapacidade()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Curral já está cheio"
            );
        }
        curral.getPorcos().add(porco);
        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    public CurralResponseDTO adicionarEsp32(Long curralId, Long esp32Id) {

        Curral curral = repository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        ESP32 esp32 = esp32Repository.findById(esp32Id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ESP32 não encontrado"
                ));

        if (curral.getEsp32() == null) {
            curral.setEsp32(new HashSet<>());
        }

        if (curral.getEsp32().contains(esp32)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse ESP32 já está vinculado a este curral"
            );
        }

        curral.getEsp32().add(esp32);

        return DataMapper.parseObject(
                repository.save(curral),
                CurralResponseDTO.class
        );
    }

    public CurralResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Curral não encontrado"
        )), CurralResponseDTO.class);
    }

    public List<CurralResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(),CurralResponseDTO.class);
    }

    public void deletar(Long id) {

        Curral curral = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        repository.delete(curral);
    }

    public CurralResponseDTO removerPorco(Long curralId, Long porcoId) {

        Curral curral = repository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        Porco porco = porcoRespository.findById(porcoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Porco não encontrado"
                ));

        if (curral.getPorcos() == null || curral.getPorcos().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse curral não possui porcos"
            );
        }

        if (!curral.getPorcos().contains(porco)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse porco não está vinculado a esse curral"
            );
        }

        curral.getPorcos().remove(porco);

        return DataMapper.parseObject(
                repository.save(curral),
                CurralResponseDTO.class
        );
    }

    public CurralResponseDTO removerEsp32(Long curralId, Long esp32Id) {

        Curral curral = repository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        ESP32 esp32 = esp32Repository.findById(esp32Id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "ESP32 não encontrado"
                ));

        if (curral.getEsp32() == null || curral.getEsp32().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse curral não possui ESP32 vinculados"
            );
        }

        if (!curral.getEsp32().contains(esp32)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse ESP32 não está vinculado a esse curral"
            );
        }

        curral.getEsp32().remove(esp32);

        return DataMapper.parseObject(
                repository.save(curral),
                CurralResponseDTO.class
        );
    }
}
