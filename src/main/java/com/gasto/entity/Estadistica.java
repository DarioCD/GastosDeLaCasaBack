package com.gasto.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Estadistica")
@Data
public class Estadistica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEstadistica;

	@Column
	private LocalDate fecha;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_casa")
	private Casa casa;

	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "gasto_estadistica", joinColumns = @JoinColumn(name = "id_estadistica"), inverseJoinColumns = @JoinColumn(name = "id_gasto"))
	private List<Gasto> gastos;

}
