package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.VariavelAmbienteCreateDTO;
import org.example.casodeuso2.dto.VariavelAmbienteResponseDTO;
import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class VariavelAmbienteService {

    private final VariavelAmbienteRepository repository;
    private final LimiteAmbientalRepository limiteAmbientalRepository;

    @Autowired
    public VariavelAmbienteService(
            VariavelAmbienteRepository repository,
            LimiteAmbientalRepository limiteAmbientalRepository) {

        this.repository = repository;
        this.limiteAmbientalRepository = limiteAmbientalRepository;
    }

    // SALVAR
    public VariavelAmbienteResponseDTO salvar(
            VariavelAmbienteCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados da variável ambiente inválidos"
            );
        }

        VariavelAmbiente variavelAmbiente =
                DataMapper.parseObject(dto, VariavelAmbiente.class);

        return DataMapper.parseObject(
                repository.save(variavelAmbiente),
                VariavelAmbienteResponseDTO.class
        );
    }

    // EDITAR
    public VariavelAmbienteResponseDTO editar(
            Long variavelAmbienteId,
            VariavelAmbienteCreateDTO dto) {

        VariavelAmbiente variavelAmbiente =
                repository.findById(variavelAmbienteId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável ambiente não encontrada"
                        ));

        variavelAmbiente.setNome(dto.getNome());
        variavelAmbiente.setUnidade(dto.getUnidade());
        variavelAmbiente.setDescricao(dto.getDescricao());

        return DataMapper.parseObject(
                repository.save(variavelAmbiente),
                VariavelAmbienteResponseDTO.class
        );
    }

    // ADICIONAR LIMITE AMBIENTAL
    public VariavelAmbienteResponseDTO adicionarLimiteAmbiental(
            Long variavelAmbientalId,
            Long limiteAmbientalId) {

        VariavelAmbiente variavelAmbiente =
                repository.findById(variavelAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável de ambiente não encontrada"
                        ));

        LimiteAmbiental limiteAmbiental =
                limiteAmbientalRepository.findById(limiteAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        if (variavelAmbiente.getLimite() != null &&
                Objects.equals(
                        variavelAmbiente.getLimite().getId(),
                        limiteAmbiental.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse limite já está vinculado a esta variável ambiente"
            );
        }

        variavelAmbiente.setLimite(limiteAmbiental);

        return DataMapper.parseObject(
                repository.save(variavelAmbiente),
                VariavelAmbienteResponseDTO.class
        );
    }

    // LISTAR TODOS
    public List<VariavelAmbienteResponseDTO> listar() {

        List<VariavelAmbiente> variaveis =
                repository.findAll();

        if (variaveis.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhuma variável ambiente encontrada"
            );
        }

        return DataMapper.parseListObjects(
                variaveis,
                VariavelAmbienteResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public VariavelAmbienteResponseDTO buscarPorId(Long id) {

        VariavelAmbiente variavelAmbiente =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável ambiente não encontrada"
                        ));

        return DataMapper.parseObject(
                variavelAmbiente,
                VariavelAmbienteResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        VariavelAmbiente variavelAmbiente =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável ambiente não encontrada"
                        ));

        repository.delete(variavelAmbiente);
    }

    // REMOVER LIMITE AMBIENTAL
    public VariavelAmbienteResponseDTO removerLimiteAmbiental(
            Long variavelAmbientalId,
            Long limiteAmbientalId) {

        VariavelAmbiente variavelAmbiente =
                repository.findById(variavelAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Variável ambiente não encontrada"
                        ));

        LimiteAmbiental limiteAmbiental =
                limiteAmbientalRepository.findById(limiteAmbientalId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        if (variavelAmbiente.getLimite() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Essa variável ambiente não possui limite ambiental"
            );
        }

        if (!Objects.equals(
                variavelAmbiente.getLimite().getId(),
                limiteAmbiental.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse limite não está vinculado a esta variável ambiente"
            );
        }

        variavelAmbiente.setLimite(null);

        return DataMapper.parseObject(
                repository.save(variavelAmbiente),
                VariavelAmbienteResponseDTO.class
        );
    }
}