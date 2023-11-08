package com.gasto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Gasto;
import com.gasto.service.EstadisticaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EstadisticaController {

	@Autowired
	EstadisticaService estadisticaService;

//	@PostMapping("/estadistica/{idCasa}")
//	public String createEstadistica(@RequestBody Estadistica estadistica, @PathVariable Long idCasa) {
//		return estadisticaService.createEstadistica(estadistica, idCasa);
//	}

	@PutMapping("/estadistica/{idEstadistica}/casa/{idCasa}")
	public ResponseEntity<?> addEstadisticaToCasa(@PathVariable Long idEstadistica, @PathVariable Long idCasa) {
		return estadisticaService.addEstadisticaToCasa(idEstadistica, idCasa);
	}
	
	@GetMapping("/estadistica/mes/{idCasa}")
	public ResponseEntity<?> getEstadisticaDelMes(@PathVariable Long idCasa, @RequestBody List<Gasto> gastosFijos) {
		return estadisticaService.getEstadisticaPorMes(idCasa, gastosFijos);
	}
	
	@GetMapping("estadistica/casa/{idCasa}")
	public ResponseEntity<?> getEstadisticaByIdCasa (@PathVariable Long idCasa) {
		try {
			return estadisticaService.getUltimaEstadisticaByCasa(idCasa);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GetMapping("estadistica/all/casa/{idCasa}")
	public ResponseEntity<?> getAllEstadisticasFromCasa (@PathVariable Long idCasa) {
		try {
			return estadisticaService.getUltimaEstadisticaByCasa(idCasa);
		} catch (Exception e) {
			throw e;
		}
	}

}
