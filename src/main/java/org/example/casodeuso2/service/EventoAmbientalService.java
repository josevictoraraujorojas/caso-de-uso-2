package org.example.casodeuso2.service;

import org.example.casodeuso2.dto.EventoAmbientalCreateDTO;
import org.example.casodeuso2.dto.EventoAmbientalResponseDTO;
import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.EventoAmbiental;
import org.example.casodeuso2.model.VariavelAmbiente;
import org.example.casodeuso2.repository.CurralRepository;
import org.example.casodeuso2.repository.EventoAmbientalRepository;
import org.example.casodeuso2.repository.VariavelAmbienteRepository;
import org.example.casodeuso2.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public EventoAmbientalResponseDTO salvar(EventoAmbientalCreateDTO eventoAmbientalCreateDTO) {
        return DataMapper.parseObject(repository.save(DataMapper.parseObject(eventoAmbientalCreateDTO,EventoAmbiental.class)),EventoAmbientalResponseDTO.class);
    }


    public EventoAmbientalResponseDTO adicionarVaravelAmbiente(Long eventoAmbientalId, Long variavelAmbienteId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        VariavelAmbiente variavelAmbiente = variavelAmbienteRepository.findById(variavelAmbienteId).orElseThrow(() -> new RuntimeException("Variavel de Ambiente não encontrado"));

        if (eventoAmbiental.getVariaveisAmbiente()==null){
            eventoAmbiental.setVariaveisAmbiente(variavelAmbiente);
            return DataMapper.parseObject(repository.save(eventoAmbiental), EventoAmbientalResponseDTO.class);
        }
        if (Objects.equals(eventoAmbiental.getVariaveisAmbiente().getId(), variavelAmbiente.getId())) {
            throw new RuntimeException("Essa Varivel de Ambiente já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setVariaveisAmbiente(variavelAmbiente);
        return DataMapper.parseObject(repository.save(eventoAmbiental), EventoAmbientalResponseDTO.class);
    }

    public EventoAmbientalResponseDTO adicionarCurral(Long eventoAmbientalId, Long curralId){
        EventoAmbiental eventoAmbiental = repository.findById(eventoAmbientalId).orElseThrow(() -> new RuntimeException("Evento Ambiental não encontrado"));
        Curral curral = curralRepository.findById(curralId).orElseThrow(() -> new RuntimeException("Curral não encontrado"));

        if (eventoAmbiental.getCurral()==null){
            eventoAmbiental.setCurral(curral);
            return DataMapper.parseObject(repository.save(eventoAmbiental),EventoAmbientalResponseDTO.class);
        }

        if (Objects.equals(eventoAmbiental.getCurral().getId(), curral.getId())) {
            throw new RuntimeException("Esse curral já esta vinculado a este Evento Ambiental");
        }
        eventoAmbiental.setCurral(curral);
        return DataMapper.parseObject(repository.save(eventoAmbiental),EventoAmbientalResponseDTO.class);
    }

    // listar todos
    public List<EventoAmbientalResponseDTO> listar() {
        return DataMapper.parseListObjects(repository.findAll(), EventoAmbientalResponseDTO.class);
    }

    // buscar por id
    public EventoAmbientalResponseDTO buscarPorId(Long id) {
        return DataMapper.parseObject(repository.findById(id).orElseThrow(() -> new RuntimeException("Evento ambiental não encontrado")), EventoAmbientalResponseDTO.class);
    }

    // deletar
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
