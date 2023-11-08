package com.gasto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gasto.entity.Categoria;
import com.gasto.entity.Gasto;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	@Query("SELECT c.gastos FROM Categoria c WHERE c.nombre = 'Fijo'")
	List<Gasto> findGastosByCategoriaFijo();

}
