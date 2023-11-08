package com.gasto.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gasto.entity.Casa;
import com.gasto.entity.Estadistica;
import com.gasto.entity.Gasto;
import com.gasto.repository.CasaRepository;
import com.gasto.repository.EstadisticaRepository;
import com.gasto.repository.GastoRepository;

@Service
public class EstadisticaService {

	@Autowired
	EstadisticaRepository estadisticaRepository;

	@Autowired
	GastoRepository gastoRepository;

	@Autowired
	CasaRepository casaRepository;

//	public Estadistica createEstadistica(Estadistica estadistica) {
//		try {
//			LocalDate fechaActual = LocalDate.now();
//			YearMonth yearMonth = YearMonth.from(fechaActual);
//			int year = yearMonth.getYear();
//			int month = yearMonth.getMonthValue();
//
//			// Verifica si ya existe una estadística para el mes actual
//			Estadistica existingEstadistica = estadisticaRepository.findByYearAndMonth(year, month);
//
//			if (existingEstadistica != null) {
//				// Si ya existe, simplemente devuelve la existente
//				return existingEstadistica;
//			} else {
//				// Si no existe, crea una nueva estadística con la fecha actual
//				estadistica.setFecha(fechaActual);
//				estadisticaRepository.save(estadistica);
//				return estadistica;
//			}
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	public ResponseEntity<?> addEstadisticaToCasa(Long idEstadistica, Long idCasa) {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Estadistica> _estadistica = estadisticaRepository.findById(idEstadistica);
			Optional<Casa> _casa = casaRepository.findById(idCasa);

			if (_estadistica.isPresent() && _casa.isPresent()) {
				Estadistica estadistica = _estadistica.get();
				Casa casa = _casa.get();

				estadistica.setCasa(casa);
				casa.getEstadisticas().add(estadistica);

				casaRepository.save(casa);
				estadisticaRepository.save(estadistica);
				response.put("message", estadistica);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "Error, la estadística " + _estadistica.get().getIdEstadistica()
						+ " no existe, o la casa :" + _casa.get().getNombre() + " no existe");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.put("message", "Error al añadir la estadística a la casa");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> getEstadisticaPorMes(Long idCasa, List<Gasto> gastosFijo) {
		Map<String, Object> response = new HashMap<>();
		try {
			LocalDate fechaActual = LocalDate.now();
			YearMonth yearMonth = YearMonth.from(fechaActual);
			int year = yearMonth.getYear();
			int month = yearMonth.getMonthValue();

			Optional<Casa> casaOptional = casaRepository.findById(idCasa);

			if (casaOptional.isPresent()) {
				Casa casa = casaOptional.get();

				List<Estadistica> estadisticas = casa.getEstadisticas();

				Estadistica estadisticaActual = estadisticas.stream().filter(estadistica -> {
					YearMonth estadisticaYearMonth = YearMonth.from(estadistica.getFecha());
					return estadisticaYearMonth.getYear() == year && estadisticaYearMonth.getMonthValue() == month;
				}).findFirst().orElse(null);

				if (estadisticaActual == null) {
					Estadistica nuevaEstadistica = new Estadistica();
					nuevaEstadistica.setFecha(fechaActual);
					nuevaEstadistica.setCasa(casa);
					nuevaEstadistica.setGastos(new ArrayList<>());

					// Copiar los gastos de la estadística anterior (si existe)
					if (!estadisticas.isEmpty()) {
						Estadistica estadisticaAnterior = estadisticas.get(estadisticas.size() - 1);
						for (Gasto gasto : estadisticaAnterior.getGastos()) {
							if (gasto.getCategorias().stream().anyMatch(categoria -> categoria.getIdCategoria() == 1)) {
						        nuevaEstadistica.getGastos().add(gasto);
						    }
						}
					}

					estadisticaRepository.save(nuevaEstadistica);
					response.put("message", nuevaEstadistica.getGastos());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

				response.put("message", estadisticaActual.getGastos());
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "Error al buscar/crear la estadística");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<?> getUltimaEstadisticaByCasa(Long idCasa) {
		Optional<Casa> _casa = casaRepository.findById(idCasa);

		Map<String, Object> response = new HashMap<>();

		if (_casa.isPresent()) {
			Casa casa = _casa.get();

			List<Estadistica> estadisticas = casa.getEstadisticas();

			if (!estadisticas.isEmpty()) {
				estadisticas.sort((est1, est2) -> est2.getIdEstadistica().compareTo(est1.getIdEstadistica()));

				response.put("message", estadisticas.get(0));
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "Error al conseguir la última estadística");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		}

		response.put("message", "Error en el server");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<?> getAllEstadisticasFromCasa(Long idCasa) {

		Optional<Casa> _casaOptional = casaRepository.findById(idCasa);

		Map<String, Object> response = new HashMap<>();

		try {
			if (_casaOptional.isPresent()) {
				Casa casa = _casaOptional.get();
				response.put("message", casa.getEstadisticas());
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", "Error al encontrar las estadísticas");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("message", "Error al encontrar las estadísticas");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
