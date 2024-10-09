package com.dss.spring.data.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseExportService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public byte[] exportDatabaseToSql() {
		 StringBuilder sqlScript = new StringBuilder();
	        sqlScript.append("INSERT INTO products (id, name, price, description) VALUES\n");

	        List<Product> products = productRepo.findAll(); // Obtener todos los productos

	        for (int i = 0; i < products.size(); i++) {
	            Product product = products.get(i);
	            sqlScript.append(String.format("(%d, '%s', %.2f)", 
	                    product.getId(), 
	                    product.getNombre() != null ? product.getNombre().replace("'", "''") : product.getNombre(), // Escapar comillas simples
	                    product.getPrecio()
	                   )); // Escapar comillas simples

	            if (i < products.size() - 1) {
	                sqlScript.append(",\n"); // Agregar coma después de cada línea, excepto la última
	            } else {
	                sqlScript.append(";\n"); // Terminar la instrucción SQL
	            }
	        }

	        return sqlScript.toString().getBytes(); // Devolver el script como byte array
	    }
	}