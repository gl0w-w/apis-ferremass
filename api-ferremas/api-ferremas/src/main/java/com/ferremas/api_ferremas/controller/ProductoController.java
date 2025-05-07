package com.ferremas.api_ferremas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferremas.api_ferremas.model.Producto;
import com.ferremas.api_ferremas.model.Local;
import com.ferremas.api_ferremas.repository.ProductoRepository;
import com.ferremas.api_ferremas.repository.LocalRepository;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private LocalRepository localRepository;

    @GetMapping
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        List<Local> locales = producto.getLocales();
        if (locales != null) {
            for (Local local : locales) {
                Optional<Local> localExistente = localRepository.findById(local.getId());
                if (!localExistente.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            }
        }

        Producto nuevoProducto = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (producto.getLocales() != null) {
            for (Local local : producto.getLocales()) {
                Optional<Local> localExistente = localRepository.findById(local.getId());
                if (!localExistente.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            }
        }

        producto.setId(id);
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarParcial(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Producto productoExistente = productoRepository.findById(id).get();
        if (producto.getNombre() != null) productoExistente.setNombre(producto.getNombre());
        if (producto.getCategoria() != null) productoExistente.setCategoria(producto.getCategoria());
        if (producto.getPrecio() != null) productoExistente.setPrecio(producto.getPrecio());
        if (producto.getStock() != 0) productoExistente.setStock(producto.getStock());

        if (producto.getLocales() != null) {
            for (Local local : producto.getLocales()) {
                Optional<Local> localExistente = localRepository.findById(local.getId());
                if (!localExistente.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            }
            productoExistente.setLocales(producto.getLocales());
        }

        return ResponseEntity.ok(productoRepository.save(productoExistente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

