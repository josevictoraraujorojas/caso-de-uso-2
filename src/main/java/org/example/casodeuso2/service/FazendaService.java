package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.FazendaCreateDTO;
import org.example.casodeuso2.dto.FazendaResponseDTO;
import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.FazendaRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class FazendaService {
    private final FazendaRepository repository;
    private final CurralRepository curralRepository;

    @Autowired
    public FazendaService(FazendaRepository repository, CurralRepository curralRepository) {
        this.repository = repository;
        this.curralRepository = curralRepository;
    }

    // salvar
    public FazendaResponseDTO salvar(FazendaCreateDTO fazendaCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(fazendaCreateDTO, Fazenda.class)),FazendaResponseDTO.class);
    }

    public FazendaResponseDTO adicionarCurral(Long fazendaId, Long curralId){
        Fazenda fazenda = repository.findById(fazendaId)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada"));

        Curral curral = curralRepository.findById(curralId)
                .orElseThrow(() -> new RuntimeException("Curral não encontrada"));

        if (fazenda.getCurrais() == null){
            fazenda.setCurrais(new HashSet<>());
        }

        if (fazenda.getCurrais().contains(curral)) {
            throw new RuntimeException("Esse curral já está vinculado a essa fazenda");
        }

        fazenda.getCurrais().add(curral);


        return DataMapper.parseObject(repository.save(fazenda), FazendaResponseDTO.class);
    }

    // listar todos
    public List<FazendaResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(), FazendaResponseDTO.class);
    }

    // buscar por id
    public FazendaResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Fazenda não encontrado")), FazendaResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public FazendaResponseDTO removerCurral(Long fazendaId, Long curralId){
        Fazenda fazenda = repository.findById(fazendaId)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrada"));

        Curral curral = curralRepository.findById(curralId)
                .orElseThrow(() -> new RuntimeException("Curral não encontrada"));

        if (fazenda.getCurrais() == null){
            throw new RuntimeException("Essa fazenda não possui currais");
        }

        if (!fazenda.getCurrais().contains(curral)) {
            throw new RuntimeException("Esse curral não está vinculado a essa fazenda");
        }

        fazenda.getCurrais().remove(curral);


        return DataMapper.parseObject(repository.save(fazenda), FazendaResponseDTO.class);
    }
}
