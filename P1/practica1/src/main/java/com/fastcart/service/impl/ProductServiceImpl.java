package com.fastcart.service.impl;

import org.springframework.stereotype.Service;

import com.fastcart.dto.ProductDto;
import com.fastcart.model.Product;
import com.fastcart.repository.CartItemRepo;
import com.fastcart.repository.ProductRepo;
import com.fastcart.service.interf.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	// Repositorios necesarios
	private final ProductRepo productRepo;
	private final CartItemRepo cartItemRepo;

	// Constructor
	public ProductServiceImpl(ProductRepo productRepo, CartItemRepo cartItemRepo) {
		this.productRepo = productRepo;
		this.cartItemRepo = cartItemRepo;
	}

	// Métodos
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public Optional<Product> getProductById(Long id) {
		return productRepo.findById(id);
	}

	public boolean doesProductExist(Long id) {
		return productRepo.existsById(id);
	}

	public Product saveProduct(ProductDto product) {
		Product new_product = new Product();

		new_product.setNombre(product.getNombre());
		new_product.setPrecio(product.getPrecio());

		return productRepo.save(new_product);
	}

	public Product editProduct(ProductDto product) {
		Product edited_product = productRepo.getReferenceById(product.getId());

		edited_product.setNombre(product.getNombre());
		edited_product.setPrecio(product.getPrecio());

		return productRepo.save(edited_product);
	}

	public boolean deleteProduct(Long id) {
		// Buscar el producto por ID
		Product product = productRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

		cartItemRepo.deleteByProductId(id); // Asegúrate de tener este método en tu CartItemRepo

		// Eliminar el producto. Esto eliminará también los CartItem asociados
		productRepo.delete(product);
		return true;
	}

	public List<Product> searchAndFilterProducts(String name, Double minPrice, Double maxPrice) {
		return productRepo.findByNombreContainingIgnoreCaseAndPrecioBetween(name, minPrice, maxPrice);
	}

	public byte[] exportProducts() {
		// Creamos el script
		StringBuilder sqlScript = new StringBuilder();
		
		//Creamos la tabla
		sqlScript.append("CREATE TABLE IF NOT EXISTS products (\n")
		          .append("    id BIGINT PRIMARY KEY,\n")
		          .append("    name VARCHAR(255) NOT NULL,\n")
		          .append("    price DECIMAL(10, 2) NOT NULL\n")
		          .append(");\n");
		
		//Primera linea del INSERT
		sqlScript.append("INSERT INTO products (id, name, price) VALUES\n");

		List<Product> products = productRepo.findAll(); // Obtener todos los productos

		// Para cada producto que tengamos, añadimos la línea con los valores
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			sqlScript.append(String.format("(%d, '%s', %s)", product.getId(),
					product.getNombre() != null ? product.getNombre().replace("'", "''") : product.getNombre(), // Escapar
																												// comillas
																												// simples
					Double.toString(product.getPrecio()).replace(",", "."))); // Escapar comillas simples

			if (i < products.size() - 1) {
				sqlScript.append(",\n"); // Agregar coma después de cada línea, excepto la última
			} else {
				sqlScript.append(";\n"); // Terminar la instrucción SQL
			}
		}

		return sqlScript.toString().getBytes(); // Devolver el script como byte array
	}
}
