package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Sensor;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariavelAmbienteService {
    private final VariavelAmbienteRepository repository;

    public VariavelAmbienteService(VariavelAmbienteRepository repository) {
        this.repository = repository;
    }

    // salvar
    public VariavelAmbiente salvar(VariavelAmbiente variavelAmbiente) {
        return repository.save(variavelAmbiente);
    }

    // listar todos
    public List<VariavelAmbiente> listar() {
        return (List<VariavelAmbiente>) repository.findAll();
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
