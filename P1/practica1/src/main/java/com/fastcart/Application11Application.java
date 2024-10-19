package com.fastcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.fastcart.dto.UserDto;
import com.fastcart.model.Role;

import com.fastcart.service.interf.UserService;

@SpringBootApplication
public class Application11Application {
	public static void main(String[] args) {
		SpringApplication.run(Application11Application.class, args);
	}

	@Bean
	CommandLineRunner jpaSample(UserService userService) {
		return (args) -> {
			if (!userService.doesThisUserExist("admin")) {
				UserDto admin = new UserDto("admin", "admin123");
	            admin.setRole(Role.ROLE_ADMIN);
	            userService.register(admin);
	        }
			if (!userService.doesThisUserExist("user")) {
				UserDto user = new UserDto("user", "user123");
				user.setRole(Role.ROLE_USER);
	            userService.register(user);
	        }
		};
	}

}