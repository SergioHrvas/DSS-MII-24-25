package com.fastcart.dto;

import com.fastcart.model.Product;

import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private String productName;
    private double productPrice;
    private int productNum;

    public CartItemDto(Product product, int productNum) {
        this.productId = product.getId();
        this.productName = product.getNombre(); // Ajusta según los campos de tu Product
        this.productPrice = product.getPrecio(); // Ajusta según los campos de tu Product
        this.productNum = productNum;
    }
}