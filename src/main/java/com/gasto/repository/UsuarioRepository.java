package com.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gasto.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findOneByEmail(String email);

}
