package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.FazendaRepository;
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
    public Fazenda salvar(Fazenda fazenda) {
        return repository.save(fazenda);
    }

    public Fazenda adicionarCurral(Long fazendaId, Long curralId){
        Fazenda fazenda = repository.findById(fazendaId).orElseThrow(() -> new RuntimeException("Fazenda não encontrada"));
        Curral curral = curralRepository.findById(curralId).orElseThrow(() -> new RuntimeException("Curral não encontrada"));

        if (fazenda.getCurrais()==null){
            fazenda.setCurrais(new HashSet<>());
        }

        if (fazenda.getCurrais().contains(curral)) {
            throw new RuntimeException("Esse curral já está vinculado a essa fazenda");
        }

        fazenda.getCurrais().add(curral);
        return repository.save(fazenda);
    }

    // listar todos
    public List<Fazenda> listar() {
        return (List<Fazenda>) repository.findAll();
    }

    // buscar por id
    public Fazenda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fazenda não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
