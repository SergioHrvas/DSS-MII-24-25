package com.fastcart.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcart.dto.ProductDto;
import com.fastcart.model.Product;
import com.fastcart.service.DatabaseExportService;
import com.fastcart.service.ProductService;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
	private DatabaseExportService databaseExportService;
	private final ProductService productService;

	public AdminController(ProductService productService, DatabaseExportService databaseExportService) {
		this.productService = productService;
		this.databaseExportService = databaseExportService;
	}

	@PostMapping(value = "/products")
	public Product saveProduct(@RequestBody ProductDto newProduct) {
		return productService.saveProduct(newProduct);
		System.out.println(newProduct)
	}

	@DeleteMapping("/products/{id}")
	public boolean deleteProduct(@PathVariable Long id) {
		if (productService.doesProductExist(id)) {
			productService.deleteProduct(id);
			return true;
		}
		return false;
	}

	@GetMapping("/products/export")
	public ResponseEntity<byte[]> exportProducts() {
		byte[] sqlScript = databaseExportService.exportDatabaseToSql();
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"products.sql\"")
				.contentType(MediaType.TEXT_PLAIN).body(sqlScript);
	}

}
