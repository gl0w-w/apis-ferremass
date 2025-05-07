package com.ferremas.api_ferremas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferremas.api_ferremas.model.Producto;
import com.ferremas.api_ferremas.model.Local;
import com.ferremas.api_ferremas.repository.LocalRepository;

@RestController
@RequestMapping("/locales")
public class LocalController {

    @Autowired
    private LocalRepository localRepository;

    @GetMapping
    public List<Local> getLocales() {
        return localRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocal(@PathVariable Long id) {
        Optional<Local> local = localRepository.findById(id);
        return local.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Local> crearLocal(@RequestBody Local local) {
        if (local.getNombre() == null || local.getDireccion() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Local nuevoLocal = localRepository.save(local);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLocal);
    }

    @GetMapping("/{id}/productos")
    public List<Producto> getProductosEnLocal(@PathVariable Long id) {
        Optional<Local> local = localRepository.findById(id);
        return local.map(Local::getProductos).orElseGet(List::of);
    }
}
