package com.dss.spring.data.rest;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.websocket.server.PathParam;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping("/{id}")
    List<CartItemDTO> all(@PathVariable Long id){
    	return cartService.getProductsCart(id);
    }
    
    @PostMapping("/{id}")
    public void addCartItem(@PathVariable Long id, @RequestBody ObjectNode json){
    	Long idProduct = json.get("idProduct").asLong();
    	int num = json.get("num").asInt();
    	cartService.addItemCart(id, idProduct, num);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteCartItem(@PathVariable Long id, @RequestParam Long idProduct, @RequestParam int num) {
    	return cartService.updateOrDeleteProductCart(id, idProduct, num);
    } 
        
}
