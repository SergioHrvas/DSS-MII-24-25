package com.dss.spring.data.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ProductUriController {
    private final ProductService productService;

    @Autowired
    public ProductUriController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/productos")
    public String productos() {
        return "productos"; // Thymeleaf buscará el archivo en templates/productos.html
    }
    
    @GetMapping("/nuevo-producto")
    public String newProduct() {
        return "formulario-product"; // Thymeleaf buscará el archivo en templates/formulario-product.html
    }
    
    
    @GetMapping("/editar-producto/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
    	Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        model.addAttribute("product", product);
    	return "formulario-product"; // Thymeleaf buscará el archivo en templates/formulario-product.html
    }
}
