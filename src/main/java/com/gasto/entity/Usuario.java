package com.gasto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String nombre;

	@Column
	private String apellidos;

	@Column
	private float sueldo;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "casa_id")
	private Casa casa;

}
