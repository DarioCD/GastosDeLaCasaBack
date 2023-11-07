package com.gasto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gasto.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
