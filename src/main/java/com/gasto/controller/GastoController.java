package com.gasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Gasto;
import com.gasto.service.GastoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class GastoController {

	@Autowired
	GastoService gastoService;

	@PostMapping("/gasto")
	public ResponseEntity<Gasto> addGasto(@RequestBody Gasto gasto) {
		return gastoService.addGasto(gasto);
	}
	@PutMapping("/gasto/{idGasto}/categoria/{idCategoria}")
	public ResponseEntity<?> addGastoToCategoria(@PathVariable Long idGasto, @PathVariable Long idCategoria) {
		return gastoService.addGastoToCategoria(idGasto, idCategoria);
	}

	@PutMapping("/gasto/{idGasto}")
	public ResponseEntity<?> editGasto(@RequestBody Gasto gasto, @PathVariable Long idGasto) {
		return gastoService.editGasto(gasto, idGasto);
	}
}
