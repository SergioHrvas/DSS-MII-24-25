package com.dss.spring.data.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;


@SpringBootApplication
public class Application11Application {
	private static final Logger log = LoggerFactory.getLogger(Application11Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application11Application.class, args);
	}
	@Bean
    CommandLineRunner jpaSample(ProductRepo productRepo) {
      return (args) -> {
	//Almacenar los 2 "instancias" de Todo en la base de datos H2   
    productRepo.save(new Product());
    Product product = new Product();

    product.setPrecio(3);
    product.setNombre("Galletas");
    productRepo.save(product);
 // fetch all customers
    log.info("Todos encontrados con findAll():");
    log.info("-------------------------------");
    productRepo.findAll().forEach(products -> {
      log.info(products.toString());
    });
    log.info("");
    /*RestTemplate restTemplate = new RestTemplate();
    //Ahora los vamos a obtener del servidor REST	   
    Product firstProduct = restTemplate.getForObject("http://localhost:8080/rest/tasks/1", Product.class);
    Product secondProduct = restTemplate.getForObject("http://localhost:8080/rest/tasks/2", Product.class);
    System.out.println(firstProduct);
    System.out.println(secondProduct);   
    
    //Envio y validacion
  //  ResponseEntity<Todo> postForEntity = 
  //  restTemplate.postForEntity("http://localhost:8080/rest/tasks/", newTodo, Todo.class);
  //  System.out.println(postForEntity);
    
  //Ejemplo de post
    Product newProduct = new Product();
    newProduct.setNombre("Manzanas");
    newProduct.setPrecio(2);

    ResponseEntity<Product> postForEnt = restTemplate.postForEntity("http://localhost:8080/rest/tasks", newProduct, Product.class);
    System.out.println("Entidad posteada en repo"+postForEnt);
    
    ResponseEntity<Product> postForEnt2 = restTemplate.postForEntity("http://localhost:8080/rest/tasks", newProduct, Product.class);
    System.out.println("Entidad posteada en repo"+postForEnt2);
    */};
  }


}