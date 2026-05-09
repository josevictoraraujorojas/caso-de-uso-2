package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.LimiteAmbientalCreateDTO;
import org.example.casodeuso2.dto.LimiteAmbientalResponseDTO;
import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LimiteAmbientalService {

    private final LimiteAmbientalRepository repository;

    @Autowired
    public LimiteAmbientalService(
            LimiteAmbientalRepository repository) {

        this.repository = repository;
    }

    // SALVAR
    public LimiteAmbientalResponseDTO salvar(
            LimiteAmbientalCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do limite ambiental inválidos"
            );
        }

        LimiteAmbiental limiteAmbiental =
                DataMapper.parseObject(dto, LimiteAmbiental.class);

        return DataMapper.parseObject(
                repository.save(limiteAmbiental),
                LimiteAmbientalResponseDTO.class
        );
    }

    // EDITAR
    public LimiteAmbientalResponseDTO editar(
            Long limiteAmbientalId,
            LimiteAmbientalCreateDTO dto) {

        LimiteAmbiental limiteAmbiental =
                repository.findById(limiteAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        limiteAmbiental.setValorMax(dto.getValorMax());
        limiteAmbiental.setValorMin(dto.getValorMin());

        return DataMapper.parseObject(
                repository.save(limiteAmbiental),
                LimiteAmbientalResponseDTO.class
        );
    }

    // LISTAR TODOS
    public List<LimiteAmbientalResponseDTO> listar() {

        List<LimiteAmbiental> limites =
                repository.findAll();

        if (limites.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum limite ambiental encontrado"
            );
        }

        return DataMapper.parseListObjects(
                limites,
                LimiteAmbientalResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public LimiteAmbientalResponseDTO buscarPorId(Long id) {

        LimiteAmbiental limiteAmbiental =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        return DataMapper.parseObject(
                limiteAmbiental,
                LimiteAmbientalResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        LimiteAmbiental limiteAmbiental =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        repository.delete(limiteAmbiental);
    }
}