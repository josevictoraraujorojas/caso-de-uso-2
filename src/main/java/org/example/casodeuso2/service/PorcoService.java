package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.PorcoCreateDTO;
import org.example.casodeuso2.dto.PorcoResponseDTO;
import org.example.casodeuso2.model.Porco;
import org.example.casodeuso2.repository.PorcoRespository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        if (porcoCreateDTO.getNome() == null || porcoCreateDTO.getNome().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do porco inválidos"
            );
        }

        return DataMapper.parseObject(repository.save(DataMapper.parseObject(porcoCreateDTO,Porco.class)),PorcoResponseDTO.class);
    }

    public PorcoResponseDTO editar(Long porcoId,PorcoCreateDTO porcoCreateDTO){
        Porco porco = repository.findById(porcoId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        porco.setPeso(porcoCreateDTO.getPeso());
        porco.setNascimento(porcoCreateDTO.getNascimento());
        porco.setRaca(porcoCreateDTO.getRaca());
        porco.setNome(porcoCreateDTO.getNome());
        porco.setSexo(porcoCreateDTO.getSexo());
        porco.setLote(porcoCreateDTO.getLote());

        return DataMapper.parseObject(repository.save(porco),PorcoResponseDTO.class);
    }

    // listar todos
    public List<PorcoResponseDTO> listar() {
        List<Porco> porcos=
                repository.findAll();

        if (porcos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum limite ambiental encontrado"
            );
        }

        return DataMapper.parseListObjects(
                porcos,
                PorcoResponseDTO.class
        );
    }

    // buscar por id
    public PorcoResponseDTO buscarPorId(Long id) {
        Porco porco =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Porco não encontrado"
                        ));

        return DataMapper.parseObject(
                porco,
                PorcoResponseDTO.class
        );
    }

    // deletar
    public void deletar(Long id) {
        Porco porco =
                repository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Limite ambiental não encontrado"
                        ));

        repository.delete(porco);
    }
}
