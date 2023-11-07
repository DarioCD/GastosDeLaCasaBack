package com.gasto.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Estadistica createEstadistica(Estadistica estadistica) {
		try {
			LocalDate fechaActual = LocalDate.now();
			YearMonth yearMonth = YearMonth.from(fechaActual);
			int year = yearMonth.getYear();
			int month = yearMonth.getMonthValue();

			// Verifica si ya existe una estadística para el mes actual
			Estadistica existingEstadistica = estadisticaRepository.findByYearAndMonth(year, month);

			if (existingEstadistica != null) {
				// Si ya existe, simplemente devuelve la existente
				return existingEstadistica;
			} else {
				// Si no existe, crea una nueva estadística con la fecha actual
				estadistica.setFecha(fechaActual);
				estadisticaRepository.save(estadistica);
				return estadistica;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Estadistica addEstadisticaToCasa(Long idEstadistica, Long idCasa) {
		try {
			Optional<Estadistica> _estadistica = estadisticaRepository.findById(idEstadistica);
			Optional<Casa> _casa = casaRepository.findById(idCasa);

			if (_estadistica.isPresent() && _casa.isPresent()) {
				Estadistica estadistica = _estadistica.get();
				Casa casa = _casa.get();

				estadistica.setCasa(casa);
				casa.getEstadisticas().add(estadistica);

				casaRepository.save(casa);
				return estadisticaRepository.save(estadistica);
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public List<Gasto> getEstadisticaPorMes(Long idCasa, List<Gasto> gastosFijo) {
		try {
			LocalDate fechaActual = LocalDate.now();
			YearMonth yearMonth = YearMonth.from(fechaActual);
			int year = yearMonth.getYear();
			int month = yearMonth.getMonthValue();

			// Buscar la casa por su ID
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

				// Si no se encuentra la estadística del mes actual, crear una nueva
				if (estadisticaActual == null) {
					Estadistica nuevaEstadistica = new Estadistica();
					nuevaEstadistica.setFecha(fechaActual);
					nuevaEstadistica.setCasa(casa);
					// REVISAR ESTO
					nuevaEstadistica.setGastos(gastosFijo);
					estadisticaRepository.save(nuevaEstadistica);
					return nuevaEstadistica.getGastos();
				}

				return estadisticaActual.getGastos();
			} else {
				// Si no se encuentra la casa, puedes manejar la situación según tus
				// requerimientos.
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
