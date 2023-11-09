package com.gasto.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasto.entity.Casa;
import com.gasto.entity.Usuario;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.UsuarioRepository;
import com.google.gson.Gson;

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
				System.out.println("---------------------- entro ---------------------");
				String bcryptHasgRegex = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
				boolean isBcryptHash = usuario.getPassword().matches(bcryptHasgRegex);
				if (usuario.getPassword() != null && usuario.getPassword() != "" && !isBcryptHash) {
					usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
				}
				usuarioRepository.save(usuario);
				response.put("message", usuario);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "El correo " + usuario.getEmail() + " ya está en uso");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.put("message", "Error al crear el usuario");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	public Usuario getUsuarioByEmail(String email) {
		Optional<Usuario> _usuario = usuarioRepository.findOneByEmail(email);
		if (_usuario.isPresent()) {
			return _usuario.get();
		} else {
			return null;
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

	public ResponseEntity<?> checkUsuario(Usuario busuario) {
		Map<String, Object> response = new HashMap<>();

		Optional<Usuario> _usuario = usuarioRepository.findOneByEmail(busuario.getEmail());

		try {
			if (_usuario.isPresent()) {
				Usuario usuario = _usuario.get();

				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				if (passwordEncoder.matches(busuario.getPassword(), usuario.getPassword())) {
					response.put("message", "Contraseña correcta");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.put("message", "Contraseña incorrecta");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.put("message", "El correo no está registrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<?> checkUsuarioEncriptado(Usuario busuario) {

		Map<String, Object> response = new HashMap<>();

		Optional<Usuario> _usuario = usuarioRepository.findOneByEmail(busuario.getEmail());

		try {
			if (_usuario.isPresent()) {

				Usuario usuario = _usuario.get();

				if (usuario.getPassword().equals(busuario.getPassword())) {
					response.put("message", usuario);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.put("message", "La contraseña no coincide");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.put("message", "El correo no esta registrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw e;
		}

	}
	
	public MiClase decodeToken (String token) {
		String[] chunks = token.split("\\.");

	    Base64.Decoder decoder = Base64.getUrlDecoder();
	    String jsonPayload = new String(decoder.decode(chunks[1]));

	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(jsonPayload, MiClase.class);
	    } catch (Exception e) {
	        // Manejar excepciones si ocurren errores al analizar el JSON
	        e.printStackTrace();
	        return null;
	    }
	}

	public ResponseEntity<?> addUsuarioToCasa(Long idUsario, String codigocasa) {

		Optional<Usuario> _usuario = usuarioRepository.findById(idUsario);
		Optional<Casa> _casa = casaRepository.findByCodigo(codigocasa);

		Map<String, Object> response = new HashMap<>();

		if (_usuario.isPresent()) {
			Usuario newUsuario = _usuario.get();
			if (_casa.isPresent()) {
				Casa newCasa = _casa.get();
				if (newCasa.getUsuarios().contains(newUsuario)) {
					response.put("message", newUsuario.getNombre() + " " + newUsuario.getApellidos() + " ya está en "
							+ newCasa.getNombre());
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

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
				response.put("message", "El código " + codigocasa + " no corresponde a ninguna casa");

				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} else {
			response.put("message", "El usuario no existe");

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

	}

}
