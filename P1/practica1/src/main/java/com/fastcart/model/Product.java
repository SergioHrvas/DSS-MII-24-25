package com.fastcart.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private double price;
	private String imagePath;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<CartItem> cartItems =  new HashSet<>();; // Cambiado a colección
	
	public void setImagePath(String path) {
		imagePath = path;
	}
	
    public Long getId() {
        return id;
    }
	
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price; 
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price; 
    }

}
