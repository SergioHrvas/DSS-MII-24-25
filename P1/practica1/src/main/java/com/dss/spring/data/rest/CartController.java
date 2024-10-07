package com.dss.spring.data.rest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping
    List<CartItem> all(@PathVariable Long id){
    	return cartService.getProductsCart(id);
    }
    
    @GetMapping("/{id}")
    public void addCartItem(@PathVariable Long id, @RequestBody long idProduct, @RequestBody int num){
    	cartService.addItemCart(id, idProduct, num);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteCartItem(@PathVariable Long id, @RequestBody long idProduct, @RequestBody int num) {
    	return cartService.deleteProductCart(id, idProduct, num);
    } 
        
}
