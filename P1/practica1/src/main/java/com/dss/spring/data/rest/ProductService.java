package com.dss.spring.data.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	private final ProductRepo productRepo;
	
	@Autowired
	public ProductService(ProductRepo productRepo) {
		this.productRepo = productRepo;
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
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}
	
	public boolean deleteProduct(Long id) {
		if(productRepo.existsById(id)) {
			productRepo.deleteById(id);
			return true;
		}
		return false;
	}
}
