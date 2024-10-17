package com.fastcart.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fastcart.dto.UserDto;
import com.fastcart.model.Cart;
import com.fastcart.model.Product;
import com.fastcart.model.Role;
import com.fastcart.model.User;
import com.fastcart.repository.CartRepo;
import com.fastcart.repository.ProductRepo;
import com.fastcart.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	private final UserRepo userRepo;
	private final CartRepo cartRepo;
	
	PasswordEncoder passwordEncode;
	
	public UserService(UserRepo userRepo,  CartRepo cartRepo) {
		this.userRepo = userRepo;
		this.cartRepo = cartRepo;

		this.passwordEncode = new BCryptPasswordEncoder();
	}
	
	public User register(UserDto user) {
        	// Verificar si el nombre de usuario ya existe
        	if (userRepo.findByUsername(user.getUsername()).isPresent()) {
        		throw new IllegalArgumentException("El nombre de usuario ya existe.");
        	}
		
        	User new_user = new User();
			new_user.setUsername(user.getUsername());
			new_user.setPassword(passwordEncode.encode(user.getPassword())); // Encriptar la contraseña
			Role role = Role.ROLE_USER;
			new_user.setRole(role);
			
			 // Crear y asociar un nuevo carrito
	        Cart cart = new Cart();
	        new_user.setCart(cart);

	        User usuario = userRepo.save(new_user);
			
			cart.setUser(usuario);
			
			cartRepo.save(cart);
			

			return usuario;
			 
	}
}
