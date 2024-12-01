package com.fastcart.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcart.model.Product;
import com.fastcart.service.interf.ProductService;

@RestController
@RequestMapping("/api/admin/")
public class ApiAdminController {
	private final ProductService productService;

	public ApiAdminController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("save_product")
	public Product saveProduct(@RequestBody Product product) {
	    Product savedProduct = productService.saveProduct(product);

	    
		return savedProduct;
	}
	

	@DeleteMapping("delete_product/{id}")
	public boolean deleteProduct(@PathVariable Long id) {
		boolean deleted = productService.deleteProduct(id);

		return deleted;

	}
	
	@PostMapping("test")
	public String testEndpoint() {
	    System.out.println("Endpoint test ejecutado");
	    return "Funciona";
	}
}
