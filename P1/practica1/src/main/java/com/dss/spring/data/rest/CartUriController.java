package com.dss.spring.data.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class CartUriController {
    private final CartService cartService;

    public CartUriController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping("/my-cart")
    public String productos() {
        return "cart"; // Thymeleaf buscará el archivo en templates/productos.html
    }
    
}
