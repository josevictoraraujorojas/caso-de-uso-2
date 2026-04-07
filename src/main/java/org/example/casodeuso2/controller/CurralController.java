package org.example.casodeuso2.controller;

import org.example.casodeuso2.model.Curral;
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
