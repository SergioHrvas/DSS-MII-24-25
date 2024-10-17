package com.fastcart.service;
import org.springframework.stereotype.Service;

import com.fastcart.dto.ProductDto;
import com.fastcart.model.CartItem;
import com.fastcart.model.Product;
import com.fastcart.repository.CartItemRepo;
import com.fastcart.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	private final ProductRepo productRepo;
	private final CartItemRepo cartItemRepo;

	public ProductService(ProductRepo productRepo, CartItemRepo cartItemRepo) {
		this.productRepo = productRepo;
		this.cartItemRepo = cartItemRepo;
	}
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public Optional<Product> getProductById(Long id){
		return productRepo.findById(id);
	}
	
	public boolean doesProductExist(Long id){
		return productRepo.existsById(id);
	}
	
	public Product saveProduct(ProductDto product) {
		Product new_product = new Product();
		
		new_product.setNombre(product.getNombre());
		new_product.setPrecio(product.getPrecio());
		
		return productRepo.save(new_product);
	}
	
	public Product editProduct(ProductDto product) {
		Product edited_product = productRepo.getById(product.getId());
		
		edited_product.setNombre(product.getNombre());
		edited_product.setPrecio(product.getPrecio());
		
		return productRepo.save(edited_product);
	}
	
	public boolean deleteProduct(Long id) {
	    // Buscar el producto por ID
	    Product product = productRepo.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
	    
	    cartItemRepo.deleteByProductId(id);  // Asegúrate de tener este método en tu CartItemRepo
	    
	    // Eliminar el producto. Esto eliminará también los CartItem asociados
	    productRepo.delete(product);
	    return true;
	}
	
	public List<Product> searchAndFilterProducts(String name, Double minPrice, Double maxPrice) {
	    return productRepo.findByNombreContainingIgnoreCaseAndPrecioBetween(name, minPrice, maxPrice);
	}
}
