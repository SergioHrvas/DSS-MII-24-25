package com.fastcart.dto;

import com.fastcart.model.Product;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private double price;
    
    public ProductDto(String name, double price) {
        this.name = name; 
        this.price = price;
    }
}