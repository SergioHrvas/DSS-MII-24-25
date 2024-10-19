package com.fastcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fastcart.model.Product;
import com.fastcart.service.interf.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	List<Product> all() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/searchAndFilter")
	public ResponseEntity<List<Product>> searchAndFilterProducts(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "0") Double minPrice,
			@RequestParam(required = false, defaultValue = "10000") Double maxPrice) {
		List<Product> products = productService.searchAndFilterProducts(name, minPrice, maxPrice);
		return ResponseEntity.ok(products);
	}

}
