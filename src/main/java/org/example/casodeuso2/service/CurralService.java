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
import org.springframework.stereotype.Service;

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

    // salvar
    public CurralResponseDTO salvar(CurralCreateDTO curralCreateDTO) {
        Curral curral = DataMapper.parseObject(curralCreateDTO, Curral.class);
        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    public CurralResponseDTO adicionarPorco(Long curralId, Long porcoId){
        Curral curral = repository.findById(curralId).orElseThrow(()-> new RuntimeException("Curral não encontrado"));
        Porco porco = porcoRespository.findById(porcoId).orElseThrow(() -> new RuntimeException("Porco não encontrado"));

        if (curral.getPorcos()==null){
            curral.setPorcos(new HashSet<>());
        }
        if (curral.getPorcos().contains(porco)){
            throw new RuntimeException("Esse porco já esta vinculado a este curral");
        }
        if (curral.getPorcos().size() >= curral.getCapacidade()){
            throw new RuntimeException("Curral já esta cheio");
        }
        curral.getPorcos().add(porco);
        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    public CurralResponseDTO adicionarEsp32(Long curralId, Long esp32Id ){
        Curral curral = repository.findById(curralId).orElseThrow(()-> new RuntimeException("Curral não encontrado"));
        ESP32 esp32 = esp32Repository.findById(esp32Id).orElseThrow(()-> new RuntimeException("ESP32 não encontrado"));

        if (curral.getEsp32()==null){
            curral.setEsp32(new HashSet<>());
        }
        if (curral.getEsp32().contains(esp32)){
            throw new RuntimeException("Esse esp32 já esta vinculado a este curral");
        }
        curral.getEsp32().add(esp32);
        return DataMapper.parseObject(repository.save(curral), CurralResponseDTO.class);
    }

    // buscar por id
    public CurralResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Curral não encontrado")), CurralResponseDTO.class);
    }

    // listar todos
    public List<CurralResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(),CurralResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
