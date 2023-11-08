package com.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gasto.entity.Casa;

public interface CasaRepository extends JpaRepository<Casa, Long> {
	
	Optional<Casa> findByCodigo(String codigo);

}
