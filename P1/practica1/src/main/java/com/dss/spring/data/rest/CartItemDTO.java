package com.dss.spring.data.rest;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private double productPrice;
    private int productNum;

    public CartItemDTO(Product product, int productNum) {
        this.productId = product.getId();
        this.productName = product.getNombre(); // Ajusta según los campos de tu Product
        this.productPrice = product.getPrecio(); // Ajusta según los campos de tu Product
        this.productNum = productNum;
    }
}