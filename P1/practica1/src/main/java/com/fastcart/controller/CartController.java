package com.fastcart.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fastcart.dto.CartItemDTO;
import com.fastcart.service.CartService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping
    List<CartItemDTO> all(Authentication authentication){
    	return cartService.getProductsCart(authentication);
    }
    
    @PostMapping
    public void addCartItem(@RequestBody ObjectNode json, Authentication authentication){
    	Long idProduct = json.get("idProduct").asLong();
    	int num = json.get("num").asInt();
    	cartService.addItemCart(authentication, idProduct, num);
    }
    
    @DeleteMapping
    public boolean deleteCartItem(@RequestParam Long idProduct, @RequestParam int num, Authentication authentication) {
    	return cartService.updateOrDeleteProductCart(authentication, idProduct, num);
    } 
        
}
