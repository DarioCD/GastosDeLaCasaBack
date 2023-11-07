package com.gasto.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Casa")
@Data
public class Casa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCasa;

	@Column
	private String nombre;

	@Column
	private String codigo;

	@OneToMany(mappedBy = "casa")
	private List<Usuario> usuarios;
	
	@Column
	private float disponible;
	
	@OneToMany(mappedBy = "casa")
	private List<Gasto> gastos;

	

}
