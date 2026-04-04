package org.example.casodeuso2.service;

import org.example.casodeuso2.model.ESP32;
import org.example.casodeuso2.repository.ESP32Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ESP32Service {

    private final ESP32Repository repository;

    @Autowired
    public ESP32Service(ESP32Repository repository) {
        this.repository = repository;
    }

    // salvar
    public ESP32 salvarOuAtualizar(ESP32 esp32) {
        ESP32 existente = repository.findByMacAddress(esp32.getMacAddress());

        if (existente != null) {
            existente.setIp(esp32.getIp());
            existente.setNome(esp32.getNome());

            return repository.save(existente);
        }

        return repository.save(esp32);
    }

    // listar todos
    public List<ESP32> listar() {
        return (List<ESP32>) repository.findAll();
    }

    // buscar por id
    public ESP32 buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ESP32 não encontrado"));
    }

    // buscar por nome
    public List<ESP32> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}