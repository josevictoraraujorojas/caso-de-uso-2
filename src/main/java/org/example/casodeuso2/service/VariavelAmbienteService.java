package org.example.casodeuso2.service;


import org.example.casodeuso2.dto.VariavelAmbienteCreateDTO;
import org.example.casodeuso2.dto.VariavelAmbienteResponseDTO;
import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VariavelAmbienteService {
    private final VariavelAmbienteRepository repository;
    private final LimiteAmbientalRepository limiteAmbientalRepository;

    @Autowired
    public VariavelAmbienteService(VariavelAmbienteRepository repository, LimiteAmbientalRepository limiteAmbientalRepository) {
        this.repository = repository;
        this.limiteAmbientalRepository = limiteAmbientalRepository;
    }

    // salvar
    public VariavelAmbienteResponseDTO salvar(VariavelAmbienteCreateDTO variavelAmbienteCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(variavelAmbienteCreateDTO,VariavelAmbiente.class)),VariavelAmbienteResponseDTO.class);
    }

    public VariavelAmbienteResponseDTO adicionarLimiteAmbiental(Long variavelAmbientalId,Long limiteAmbientalId) {
        VariavelAmbiente variavelAmbiente = repository.findById(variavelAmbientalId).orElseThrow(() -> new RuntimeException("Variavel de Ambiente não encontrada"));
        LimiteAmbiental limiteAmbiental = limiteAmbientalRepository.findById(limiteAmbientalId).orElseThrow(() -> new RuntimeException("Limite Ambiental nâo encontrado"));
        if (variavelAmbiente.getLimite()==null){
            variavelAmbiente.setLimite(limiteAmbiental);
            return DataMapper.parseObject(repository.save(variavelAmbiente),VariavelAmbienteResponseDTO.class);
        }
        if (Objects.equals(variavelAmbiente.getLimite().getId(), limiteAmbiental.getId())) {
            throw new RuntimeException("Esse Limite já esta vinculada com esta Variavel de Ambiente");
        }
        variavelAmbiente.setLimite(limiteAmbiental);
        return DataMapper.parseObject(repository.save(variavelAmbiente),VariavelAmbienteResponseDTO.class);
    }

    // listar todos
    public List<VariavelAmbienteResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(),VariavelAmbienteResponseDTO.class);
    }
    // buscar por id
    public VariavelAmbienteResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Variavel ambiente não encontrado")),VariavelAmbienteResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
