package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.FazendaCreateDTO;
import org.example.casodeuso2.dto.FazendaResponseDTO;
import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.FazendaRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class FazendaService {

    private final FazendaRepository repository;
    private final CurralRepository curralRepository;

    @Autowired
    public FazendaService(
            FazendaRepository repository,
            CurralRepository curralRepository) {

        this.repository = repository;
        this.curralRepository = curralRepository;
    }

    // SALVAR
    public FazendaResponseDTO salvar(
            FazendaCreateDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados da fazenda inválidos"
            );
        }

        Fazenda fazenda =
                DataMapper.parseObject(dto, Fazenda.class);

        return DataMapper.parseObject(
                repository.save(fazenda),
                FazendaResponseDTO.class
        );
    }

    // EDITAR
    public FazendaResponseDTO editar(
            Long fazendaId,
            FazendaCreateDTO dto) {

        Fazenda fazenda = repository.findById(fazendaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fazenda não encontrada"
                ));

        fazenda.setNome(dto.getNome());
        fazenda.setEndereco(dto.getEndereco());

        return DataMapper.parseObject(
                repository.save(fazenda),
                FazendaResponseDTO.class
        );
    }

    // ADICIONAR CURRAL
    public FazendaResponseDTO adicionarCurral(
            Long fazendaId,
            Long curralId) {

        Fazenda fazenda = repository.findById(fazendaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fazenda não encontrada"
                ));

        Curral curral = curralRepository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        if (fazenda.getCurrais() == null) {
            fazenda.setCurrais(new HashSet<>());
        }

        if (fazenda.getCurrais().contains(curral)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse curral já está vinculado a essa fazenda"
            );
        }

        fazenda.getCurrais().add(curral);

        return DataMapper.parseObject(
                repository.save(fazenda),
                FazendaResponseDTO.class
        );
    }

    // LISTAR TODOS
    public List<FazendaResponseDTO> listar() {

        List<Fazenda> fazendas = repository.findAll();

        if (fazendas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhuma fazenda encontrada"
            );
        }

        return DataMapper.parseListObjects(
                fazendas,
                FazendaResponseDTO.class
        );
    }

    // BUSCAR POR ID
    public FazendaResponseDTO buscarPorId(Long id) {

        Fazenda fazenda = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fazenda não encontrada"
                ));

        return DataMapper.parseObject(
                fazenda,
                FazendaResponseDTO.class
        );
    }

    // DELETAR
    public void deletar(Long id) {

        Fazenda fazenda = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fazenda não encontrada"
                ));

        repository.delete(fazenda);
    }

    // REMOVER CURRAL
    public FazendaResponseDTO removerCurral(
            Long fazendaId,
            Long curralId) {

        Fazenda fazenda = repository.findById(fazendaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fazenda não encontrada"
                ));

        Curral curral = curralRepository.findById(curralId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curral não encontrado"
                ));

        if (fazenda.getCurrais() == null ||
                fazenda.getCurrais().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Essa fazenda não possui currais"
            );
        }

        if (!fazenda.getCurrais().contains(curral)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse curral não está vinculado a essa fazenda"
            );
        }

        fazenda.getCurrais().remove(curral);

        return DataMapper.parseObject(
                repository.save(fazenda),
                FazendaResponseDTO.class
        );
    }
}