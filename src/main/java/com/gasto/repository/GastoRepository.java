package com.gasto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gasto.entity.Gasto;

public interface GastoRepository extends JpaRepository<Gasto, Long>{

}
