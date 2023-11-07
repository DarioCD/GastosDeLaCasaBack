package com.gasto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Estadistica;
import com.gasto.entity.Gasto;
import com.gasto.service.EstadisticaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EstadisticaController {

	@Autowired
	EstadisticaService estadisticaService;

	@PostMapping("/estadistica")
	public Estadistica createEstadistica(@RequestBody Estadistica estadistica) {
		return estadisticaService.createEstadistica(estadistica);
	}

	@PutMapping("/estadistica/{idEstadistica}/casa/{idCasa}")
	public Estadistica addEstadisticaToCasa(@PathVariable Long idEstadistica, @PathVariable Long idCasa) {
		return estadisticaService.addEstadisticaToCasa(idEstadistica, idCasa);
	}
	
	@GetMapping("/estadistica/mes/{idCasa}")
	public List<Gasto> getEstadisticaDelMes(@PathVariable Long idCasa, @RequestBody List<Gasto> gastosFijos) {
		return estadisticaService.getEstadisticaPorMes(idCasa, gastosFijos);
	}

}
