package org.example.casodeuso2.service;


import org.example.casodeuso2.model.LimiteAmbiental;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.LimiteAmbientalRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
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
    public VariavelAmbiente salvar(VariavelAmbiente variavelAmbiente) {
        return repository.save(variavelAmbiente);
    }

    public VariavelAmbiente adicionarLimiteAmbiental(Long variavelAmbientalId,Long limiteAmbientalId) {
        VariavelAmbiente variavelAmbiente = repository.findById(variavelAmbientalId).orElseThrow(() -> new RuntimeException("Variavel de Ambiente não encontrada"));
        LimiteAmbiental limiteAmbiental = limiteAmbientalRepository.findById(limiteAmbientalId).orElseThrow(() -> new RuntimeException("Limite Ambiental nâo encontrado"));


        if (variavelAmbiente.getLimite()==null){
            variavelAmbiente.setLimite(limiteAmbiental);
            return repository.save(variavelAmbiente);
        }
        if (Objects.equals(variavelAmbiente.getLimite().getId(), limiteAmbiental.getId())) {
            throw new RuntimeException("Esse Limite já esta vinculada com esta Variavel de Ambiente");
        }
        variavelAmbiente.setLimite(limiteAmbiental);
        return repository.save(variavelAmbiente);
    }

    // listar todos
    public List<VariavelAmbiente> listar() {
        return repository.findAll();
    }
    // buscar por id
    public VariavelAmbiente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variavel ambiente não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
