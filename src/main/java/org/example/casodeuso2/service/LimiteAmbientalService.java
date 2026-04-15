package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.LimiteAmbientalCreateDTO;
import org.example.casodeuso2.dto.LimiteAmbientalResponseDTO;
import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LimiteAmbientalService {
    private final LimiteAmbientalRepository repository;

    @Autowired
    public LimiteAmbientalService(LimiteAmbientalRepository repository) {
        this.repository = repository;
    }

    // salvar
    public LimiteAmbientalResponseDTO salvar(LimiteAmbientalCreateDTO limiteAmbientalCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(limiteAmbientalCreateDTO,LimiteAmbiental.class)),LimiteAmbientalResponseDTO.class);
    }

    // listar todos
    public List<LimiteAmbientalResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(), LimiteAmbientalResponseDTO.class);
    }

    // buscar por id
    public LimiteAmbientalResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Limite ambiental não encontrado")),LimiteAmbientalResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
