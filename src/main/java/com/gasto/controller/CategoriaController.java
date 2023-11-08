package com.gasto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Categoria;
import com.gasto.entity.Gasto;
import com.gasto.service.CategoriaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@PostMapping("/categoria")
	public ResponseEntity<?> addCategoria (@RequestBody Categoria categoria) {
		return categoriaService.addCategoria(categoria);
	}
	
	@PutMapping("/categoria/{idCategoria}")
	public ResponseEntity<?> editCategoria (@RequestBody Categoria categoria, @PathVariable Long idCategoria) {
		return categoriaService.editCategoria(categoria, idCategoria);
	}
	
	@GetMapping("/prueba")
	public List<Gasto> prueba () {
		return categoriaService.getGastosByCategoriaFijo();
	}

}
