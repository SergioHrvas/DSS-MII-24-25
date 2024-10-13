package com.fastcart.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CartItem{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @JsonIgnoreProperties("items")
	@ManyToOne
	private Cart cart; // Relación con el carrito
	
	private Long idProduct;
	private int num;
	
    @Override
    public String toString() {
        // Evita imprimir el Cart completo para evitar recursión
        return "CartItem{id=" + this.id + ", product=" + idProduct + "}";
    }
}
