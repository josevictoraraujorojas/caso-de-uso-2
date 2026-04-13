package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.Curral;
import org.example.casodeuso2.model.Fazenda;
import org.example.casodeuso2.service.CurralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curral")
public class CurralController {
    private final CurralService service;

    @Autowired
    public CurralController(CurralService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public Curral criar(@RequestBody Curral curral) {
        return service.salvar(curral);
    }

    //PUT
    @PutMapping("/{curralId}/porco/{porcoId}")
    public Curral adicionarPorco(@PathVariable Long curralId , @PathVariable Long porcoId) {
        return service.adicionarPorco(curralId, porcoId);
    }

    @PutMapping("/{curralId}/esp32/{esp32Id}")
    public Curral adicionarEsp32(@PathVariable Long curralId , @PathVariable Long esp32Id) {
        return service.adicionarEsp32(curralId, esp32Id);
    }

    // GET todos
    @GetMapping
    public List<Curral> listar() {
        return service.listar();
    }

    // GET por ID
    @GetMapping("/{id}")
    public Curral buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

}
