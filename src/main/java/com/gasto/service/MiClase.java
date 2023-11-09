package com.gasto.service;

import lombok.Data;

@Data
public class MiClase {
	
	private String sub;
    private long exp;
    private String nombre;

    // Getters y setters

    @Override
    public String toString() {
        return "MiClase{" +
                "sub='" + sub + '\'' +
                ", exp=" + exp +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
