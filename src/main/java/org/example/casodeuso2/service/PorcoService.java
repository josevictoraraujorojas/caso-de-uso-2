package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.PorcoCreateDTO;
import org.example.casodeuso2.dto.PorcoResponseDTO;
import org.example.casodeuso2.model.Porco;
import org.example.casodeuso2.repository.PorcoRespository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PorcoService {
    private final PorcoRespository repository;

    @Autowired
    public PorcoService(PorcoRespository repository) {
        this.repository = repository;
    }

    // salvar
    public PorcoResponseDTO salvar(PorcoCreateDTO porcoCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(porcoCreateDTO,Porco.class)),PorcoResponseDTO.class);
    }

    // listar todos
    public List<PorcoResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(),PorcoResponseDTO.class);
    }

    // buscar por id
    public PorcoResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Porco não encontrado")),PorcoResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
