package com.ferremas.api_ferremas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferremas.api_ferremas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
