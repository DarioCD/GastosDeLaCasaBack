package com.gasto.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gasto.entity.Categoria;
import com.gasto.entity.Gasto;
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
	
	public ResponseEntity<?> addCategoria (Categoria categoria) {
		Map<String, Object> response = new HashMap<>();
		try {
			categoriaRepository.save(categoria);
			response.put("message", categoria);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("message", "Error al añadir la categoría");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> editCategoria (Categoria categoria, Long idCategoria) {
		Optional <Categoria> _categoria = categoriaRepository.findById(idCategoria);
		Map<String, Object> response = new HashMap<>();
		try {
			if (_categoria.isPresent()) {
				Categoria existingCategoria = _categoria.get();
				if (categoria.getNombre() != null) {
					existingCategoria.setNombre(categoria.getNombre());
				}
				categoriaRepository.save(existingCategoria);
				response.put("message", existingCategoria);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.put("message", "Error al editar la categoría");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("message", "Error del servidor");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public List<Gasto> getGastosByCategoriaFijo() {
        return categoriaRepository.findGastosByCategoriaFijo();
    }

}
