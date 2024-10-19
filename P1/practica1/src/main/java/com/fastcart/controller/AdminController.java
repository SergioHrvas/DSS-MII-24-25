package com.fastcart.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcart.dto.ProductDto;
import com.fastcart.model.Product;
import com.fastcart.service.interf.ProductService;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
	private final ProductService productService;

	public AdminController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(value = "/products")
	public Product saveProduct(@RequestBody ProductDto newProduct) {
		return productService.saveProduct(newProduct);
	}

	@PutMapping(value = "/products")
	public Product editProduct(@RequestBody ProductDto newProduct) {
		return productService.editProduct(newProduct);
	}

	@DeleteMapping("/products/{id}")
	public boolean deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}

	@GetMapping("/products/export")
	public ResponseEntity<byte[]> exportProducts() {
		byte[] sqlScript = productService.exportProducts();
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"products.sql\"")
				.contentType(MediaType.TEXT_PLAIN).body(sqlScript);
	}

}
