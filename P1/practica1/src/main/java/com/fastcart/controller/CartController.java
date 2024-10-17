package com.fastcart.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fastcart.dto.CartItemDto;
import com.fastcart.service.CartService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpHeaders;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping
    List<CartItemDto> all(Authentication authentication){
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
    
    
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getCartPdf(Authentication authentication) {
        byte[] pdfBytes = cartService.generateCartPdf(authentication);
     // Establecer los headers para la descarga del archivo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "cart.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
        		.headers(headers)
        		.contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

        
}
