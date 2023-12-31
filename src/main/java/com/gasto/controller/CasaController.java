package com.gasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Casa;
import com.gasto.service.CasaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CasaController {

	@Autowired
	CasaService casaService;

	@PostMapping("/casa")
	public ResponseEntity<?> save(@RequestBody Casa casa) {
		return casaService.save(casa);
	}

}
