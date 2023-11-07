package com.gasto.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Casa save(Casa casa) {
		try {
			String codigoAleatorio = UUID.randomUUID().toString();
			casa.setCodigo(codigoAleatorio);

			Casa savedCasa = casaRepository.save(casa);

			Estadistica estadistica = new Estadistica();
			estadistica.setCasa(savedCasa);

			estadistica.setFecha(LocalDate.now());

			estadisticaRepository.save(estadistica);

			return savedCasa;
		} catch (Exception e) {
			return null;
		}
	}
}
