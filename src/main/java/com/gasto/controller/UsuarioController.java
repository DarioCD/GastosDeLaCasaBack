package com.gasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/usuario")
	public Usuario save (@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}
	
	@PutMapping("/usuario/sueldo/{sueldo}/{idUsuario}")
	public Float updateSueldo (@PathVariable float sueldo, @PathVariable Long idUsuario) {
		return usuarioService.updateSueldo(sueldo, idUsuario);
	}
	
	@PutMapping("/usuario/addUsuarioToCasa/{idUser}/{idCasa}")
	public String addUsuarioToCasa (@PathVariable Long idUser, @PathVariable Long idCasa) {
		return usuarioService.addUsuarioToCasa(idUser, idCasa);
	}

}
