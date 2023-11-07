package com.gasto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gasto.entity.Categoria;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.CategoriaRepository;
import com.gasto.repository.GastoRepository;

@Service
public class CategoriaService {
	
	@Autowired
	GastoRepository gastoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	CasaRepository casaRepository;
	
	public Categoria addCategoria (Categoria categoria) {
		try {
			return categoriaRepository.save(categoria);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Categoria editCategoria (Categoria categoria, Long idCategoria) {
		Optional <Categoria> _categoria = categoriaRepository.findById(idCategoria);
		try {
			if (_categoria.isPresent()) {
				Categoria existingCategoria = _categoria.get();
				if (categoria.getNombre() != null) {
					existingCategoria.setNombre(categoria.getNombre());
				}
				return categoriaRepository.save(existingCategoria);
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

}
