package com.gasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasto.entity.Usuario;
import com.gasto.service.MiClase;
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
	
	@PostMapping("/usuario/check")
	public ResponseEntity<?> checkUsuario(@RequestBody Usuario usuario) {
		return usuarioService.checkUsuario(usuario);
	}
	
	@GetMapping("/usuario/check/encriptado")
	public ResponseEntity<?> checkUsuarioEncriptado(@RequestBody Usuario usuario) {
		return usuarioService.checkUsuarioEncriptado(usuario);
	}
	
	@PostMapping("/usuario/email")
	public Usuario getUsuarioByEmail(@RequestBody Usuario usuario) {
		return usuarioService.getUsuarioByEmail(usuario);
	}
	
	@PostMapping("/usuario/decode/token")
	public ResponseEntity<?> checkUsuarioEncriptado(@RequestBody String token) {
		return usuarioService.decodeToken(token);
	}
}
