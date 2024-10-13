package com.dss.spring.data.rest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    private DatabaseExportService databaseExportService;

    public ProductController(ProductService productService, DatabaseExportService databaseExportService) {
        this.productService = productService;
        this.databaseExportService = databaseExportService;
    }
    
    @GetMapping
    List<Product> all(){
    	return productService.getAllProducts();
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
    	return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Product saveProduct(@RequestBody Product newProduct) {
    	return productService.saveProduct(newProduct);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
    	if(productService.doesProductExist(id)){
	    	productService.deleteProduct(id);
	    	return true;
	    }
    	return false;
    } 
    

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportProducts() {
        byte[] sqlScript = databaseExportService.exportDatabaseToSql();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"products.sql\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(sqlScript);
    }
    
}
