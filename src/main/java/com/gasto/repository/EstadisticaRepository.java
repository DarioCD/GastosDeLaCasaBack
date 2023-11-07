package com.gasto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gasto.entity.Estadistica;

public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {

	@Query("SELECT e FROM Estadistica e WHERE YEAR(e.fecha) = ?1 AND MONTH(e.fecha) = ?2")
	Estadistica findByYearAndMonth(int year, int month);

}
