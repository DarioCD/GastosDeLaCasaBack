package com.gasto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gasto.entity.Casa;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.UsuarioRepository;

@Service
public class CasaService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CasaRepository casaRepository;
	
	public Casa save(Casa casa) {
		try {
			return casaRepository.save(casa);
		} catch (Exception e) {
			return null;
		}
	}
}
