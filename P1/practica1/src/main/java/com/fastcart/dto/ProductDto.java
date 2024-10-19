package com.fastcart.dto;

import lombok.Data;

@Data
public class ProductDto {
	private Long id;
    private String nombre;
    private double precio;
    
    public ProductDto(String nombre, double precio, Long id) {
        this.nombre = nombre; 
        this.precio = precio;
        this.id = id;
    }
}