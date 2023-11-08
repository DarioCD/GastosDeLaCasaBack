package com.gasto.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gasto.entity.Casa;
import com.gasto.entity.Estadistica;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.EstadisticaRepository;
import com.gasto.repository.UsuarioRepository;

@Service
public class CasaService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CasaRepository casaRepository;

	@Autowired
	EstadisticaRepository estadisticaRepository;

	public ResponseEntity<?> save(Casa casa) {
		Map<String, Object> response = new HashMap<>();
		try {
			String codigoAleatorio = UUID.randomUUID().toString();
			casa.setCodigo(codigoAleatorio);

			Casa savedCasa = casaRepository.save(casa);

			Estadistica estadistica = new Estadistica();
			estadistica.setCasa(savedCasa);

			estadistica.setFecha(LocalDate.now());

			estadisticaRepository.save(estadistica);
			
			response.put("message", savedCasa);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("message", "Error al crear la casa");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
