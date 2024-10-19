package com.fastcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartUriController {

	@GetMapping("/my-cart")
	public String productos() {
		return "cart"; // Thymeleaf buscará el archivo en templates/productos.html
	}

}
