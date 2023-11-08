package com.gasto.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gasto.entity.Casa;
import com.gasto.entity.Usuario;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CasaRepository casaRepository;

	public ResponseEntity<?> save(Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
		
		Optional<Usuario> _usuario = usuarioRepository.findByEmail(usuario.getEmail());
		
		try {
			
			if (_usuario.isEmpty()) {
				usuarioRepository.save(usuario);
				response.put("message", usuario);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				response.put("message", "El correo " + usuario.getEmail() + " ya está en uso");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.put("message", "Error al crear el usuario");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateSueldo(float sueldo, Long idUsuario) {

		Map<String, Object> response = new HashMap<>();

		Optional<Usuario> _usuario = usuarioRepository.findById(idUsuario);

		if (_usuario.isPresent()) {

			Usuario newUsuario = _usuario.get();
			newUsuario.setSueldo(sueldo);

			// Actualizar disponible de la casa
			if (newUsuario.getCasa() != null) {
				newUsuario.getCasa().setDisponible(
						newUsuario.getCasa().getDisponible() + (newUsuario.getSueldo() - _usuario.get().getSueldo()));
			}
			usuarioRepository.save(newUsuario);

			response.put("message", "Se actualizó correctamnte el sueldo de " + _usuario.get().getSueldo() + " a "
					+ newUsuario.getSueldo());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.put("message", "Error al actualizar el sueldo");

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> addUsuarioToCasa(Long idUsario, Long idCasa) {

		Optional<Usuario> _usuario = usuarioRepository.findById(idUsario);
		Optional<Casa> _casa = casaRepository.findById(idCasa);

		Map<String, Object> response = new HashMap<>();

		if (_usuario.isPresent()) {
			if (_casa.isPresent()) {

				Usuario newUsuario = _usuario.get();
				Casa newCasa = _casa.get();
				newUsuario.setCasa(newCasa);
				newCasa.getUsuarios().add(newUsuario);
				// Añado el sueldo del usuario al de la casa
				newCasa.setDisponible(newCasa.getDisponible() + newUsuario.getSueldo());

				usuarioRepository.save(newUsuario);
				casaRepository.save(newCasa);

				response.put("message", newUsuario.getNombre() + " " + newUsuario.getApellidos()
						+ " se añadió con éxtio a " + newCasa.getNombre());

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "La casa no existe");

				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} else {
			response.put("message", "El usuario no existe");

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

	}

}
