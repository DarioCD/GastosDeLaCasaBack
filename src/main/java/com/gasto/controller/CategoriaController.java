package com.gasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Categoria;
import com.gasto.service.CategoriaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@PostMapping("/categoria")
	public Categoria addCategoria (@RequestBody Categoria categoria) {
		return categoriaService.addCategoria(categoria);
	}
	
	@PutMapping("/categoria/{idCategoria}")
	public Categoria editCategoria (@RequestBody Categoria categoria, @PathVariable Long idCategoria) {
		return categoriaService.editCategoria(categoria, idCategoria);
	}

}
