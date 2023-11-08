package com.gasto.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gasto")
@Data
public class Gasto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGasto;

	@Column
	private Date fecha;

	@Column
	private String nombre;

	@Column
	private float precio;

	@ManyToMany
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCategoria")
	@JsonIdentityReference(alwaysAsId = true)
	@JoinTable(name = "gasto_categoria", joinColumns = @JoinColumn(name = "id_gasto"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
	private List<Categoria> categorias;

	@ManyToMany
	@JoinTable(name = "gasto_estadistica", joinColumns = @JoinColumn(name = "id_gasto"), inverseJoinColumns = @JoinColumn(name = "id_estadistica"))
	private List<Estadistica> estadisticas;

	public Gasto() {
		this.categorias = new ArrayList<>();
		this.estadisticas = new ArrayList<>();
	}

}
