package com.gasto.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gasto.entity.Casa;
import com.gasto.entity.Categoria;
import com.gasto.entity.Estadistica;
import com.gasto.entity.Gasto;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.CategoriaRepository;
import com.gasto.repository.EstadisticaRepository;
import com.gasto.repository.GastoRepository;

@Service
public class GastoService {

	@Autowired
	GastoRepository gastoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	CasaRepository casaRepository;

	@Autowired
	EstadisticaRepository estadisticaRepository;

	@Autowired
	EstadisticaService estadisticaService;

	public Gasto addGasto(Gasto gasto, Long idCasa) {

		try {

			LocalDate fechaActual = LocalDate.now();
			YearMonth yearMonth = YearMonth.from(fechaActual);
			int year = yearMonth.getYear();
			int month = yearMonth.getMonthValue();

			Optional<Casa> casaOptional = casaRepository.findById(idCasa);

			if (casaOptional.isPresent()) {
				Casa casa = casaOptional.get();

				// Obtener las estadísticas asociadas a la casa
				List<Estadistica> estadisticas = casa.getEstadisticas();

				// Filtrar las estadísticas para encontrar la del mes actual
				Estadistica estadisticaActual = estadisticas.stream().filter(estadistica -> {
					YearMonth estadisticaYearMonth = YearMonth.from(estadistica.getFecha());
					return estadisticaYearMonth.getYear() == year && estadisticaYearMonth.getMonthValue() == month;
				}).findFirst().orElse(null);

				gasto.getEstadisticas().add(estadisticaActual);

				return gastoRepository.save(gasto);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseEntity<?> addGastoToCategoria(Long idGasto, Long idCategoria) {
		Optional<Gasto> optionalGasto = gastoRepository.findById(idGasto);
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(idCategoria);

		Map<String, Object> response = new HashMap<>();

		try {
			if (optionalGasto.isPresent() && optionalCategoria.isPresent()) {
				Gasto gasto = optionalGasto.get();
				Categoria categoria = optionalCategoria.get();

				if (!categoria.getGastos().contains(gasto)) {
					gasto.getCategorias().add(categoria);
					categoria.getGastos().add(gasto);
					categoriaRepository.save(categoria);
					return new ResponseEntity<>(gastoRepository.save(gasto), HttpStatus.OK);
				}
			}

		} catch (DataAccessException e) {
			response.put("message", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Error al añadir gasto a la categoria");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> editGasto(Gasto gasto, Long idGasto) {
		Optional<Gasto> optionalGasto = gastoRepository.findById(idGasto);
		Map<String, Object> response = new HashMap<>();

		if (optionalGasto.isPresent()) {
			Gasto existingGasto = optionalGasto.get();
			if (gasto.getFecha() != null) {
				existingGasto.setFecha(gasto.getFecha());
			}
			if (gasto.getNombre() != null) {
				existingGasto.setNombre(gasto.getNombre());
			}
			if (gasto.getPrecio() != 0) {
				existingGasto.setPrecio(gasto.getPrecio());
			}
			return new ResponseEntity<>(gastoRepository.save(existingGasto), HttpStatus.OK);
		} else {
			response.put("message", "Error al editar el gasto");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
