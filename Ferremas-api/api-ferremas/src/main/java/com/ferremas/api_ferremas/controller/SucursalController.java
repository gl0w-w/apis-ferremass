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
import com.ferremas.api_ferremas.model.Sucursal;
import com.ferremas.api_ferremas.repository.SucursalRepository;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public List<Sucursal> getSucursales() {
        return sucursalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> getSucursal(@PathVariable Long id) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(id);
        return sucursal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getDireccion() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
        }
        Sucursal nuevaSucursal = sucursalRepository.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    @GetMapping("/{id}/productos")
    public List<Producto> getProductosEnSucursal(@PathVariable Long id) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(id);
        return sucursal.map(Sucursal::getProductos).orElseGet(List::of);
    }
}
