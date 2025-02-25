package com.fastcart.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	//private Long idProduct;
    
	@ManyToOne
    @JsonManagedReference
	private Product product;

	private int num;
	
    @Override
    public String toString() {
        // Evita imprimir el Cart completo para evitar recursión
        return "CartItem{id=" + this.id + ", product=" + product.getId() + "}";
    }
    
    public Long getId() {
        return id;
    }
    
    public Product getProduct() {
        return product; 
    }
    
    public int getNum() {
        return num;
    }
    
    public void setId(Long id) {
        this.id = id; 
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
}
