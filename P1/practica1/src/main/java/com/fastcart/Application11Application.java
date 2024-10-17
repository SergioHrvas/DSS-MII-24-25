package com.fastcart;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.fastcart.repository.ProductRepo;

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

		};
	}

}