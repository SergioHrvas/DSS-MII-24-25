package com.dss.spring.data.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
    
    
    
}
