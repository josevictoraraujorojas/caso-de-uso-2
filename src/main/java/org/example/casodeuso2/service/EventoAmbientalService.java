package org.example.casodeuso2.service;

import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.EventoAmbiental;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.EventoAmbientalRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoAmbientalService {
    private final EventoAmbientalRepository repository;
    private final CurralRepository curralRepository;
    private final VariavelAmbienteRepository variavelAmbienteRepository;

    @Autowired
    public EventoAmbientalService(EventoAmbientalRepository repository, CurralRepository curralRepository, VariavelAmbienteRepository variavelAmbienteRepository) {
        this.repository = repository;
        this.curralRepository = curralRepository;
        this.variavelAmbienteRepository = variavelAmbienteRepository;
    }

    // salvar
    public EventoAmbiental salvar(EventoAmbiental eventoAmbiental) {
        return repository.save(eventoAmbiental);
    }

    public EventoAmbiental adicionarCurral(Long eventoAmbientalId, Long curralId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        Curral curral = curralRepository.findById(curralId).orElseThrow(() -> new RuntimeException("Curral não encontrado"));

        if (eventoAmbiental.getCurral()==null){
            eventoAmbiental.setCurral(curral);
            return repository.save(eventoAmbiental);
        }
        if (eventoAmbiental.getCurral().equals(curral)){
            throw new RuntimeException("Esse curral já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setCurral(curral);
        return repository.save(eventoAmbiental);
    }

    public EventoAmbiental adicionarVaravelAmbiente(Long eventoAmbientalId, Long variavelAmbienteId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        VariavelAmbiente variavelAmbiente = variavelAmbienteRepository.findById(variavelAmbienteId).orElseThrow(() -> new RuntimeException("Variavel de Ambiente não encontrado"));

        if (eventoAmbiental.getVariaveisAmbiente()==null){
            eventoAmbiental.setVariaveisAmbiente(variavelAmbiente);
            return repository.save(eventoAmbiental);
        }
        if (eventoAmbiental.getVariaveisAmbiente().equals(variavelAmbienteId)){
            throw new RuntimeException("Essa Varivel de Ambiente já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setVariaveisAmbiente(variavelAmbiente);
        return repository.save(eventoAmbiental);
    }

    // listar todos
    public List<EventoAmbiental> listar() {
        return repository.findAll();
    }

    // buscar por id
    public EventoAmbiental buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento ambiental não encontrado"));
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
