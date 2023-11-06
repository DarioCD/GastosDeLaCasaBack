package com.gasto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gasto.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
