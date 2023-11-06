package com.gasto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Usuario save(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);
		} catch (Exception e) {
			return null;
		}
	}

	public Float updateSueldo(float sueldo, Long idUsuario) {

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

			return newUsuario.getSueldo();
		}
		return null;
	}

	public String addUsuarioToCasa(Long idUsario, Long idCasa) {

		Optional<Usuario> _usuario = usuarioRepository.findById(idUsario);
		Optional<Casa> _casa = casaRepository.findById(idCasa);

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

				return newUsuario.getNombre() + " " + newUsuario.getApellidos() + " se añadió con éxtio a "
						+ newCasa.getNombre();
			}
		}
		return "Error al añadir usuario a casa ";

	}

}
