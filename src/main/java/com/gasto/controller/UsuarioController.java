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

import com.gasto.entity.Usuario;
import com.gasto.service.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
	
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("/register/usuario")
	public ResponseEntity<?> save (@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}
	
	@PutMapping("/usuario/sueldo/{sueldo}/{idUsuario}")
	public ResponseEntity<?> updateSueldo (@PathVariable float sueldo, @PathVariable Long idUsuario) {
		return usuarioService.updateSueldo(sueldo, idUsuario);
	}
	
	@PutMapping("/usuario/{idUsuario}/codigo/{codigo}")
	public ResponseEntity<?> addUsuarioToCasa (@PathVariable Long idUsuario, @PathVariable String codigo) {
		return usuarioService.addUsuarioToCasa(idUsuario, codigo);
	}

}
