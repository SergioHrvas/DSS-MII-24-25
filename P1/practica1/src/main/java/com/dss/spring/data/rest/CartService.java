package com.dss.spring.data.rest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;
	private final CartItemRepo cartItemRepo;

	public CartService(CartRepo cartRepo, ProductRepo productRepo, CartItemRepo cartItemRepo) {
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartItemRepo = cartItemRepo;
	}
	
	public List<CartItemDTO> getProductsCart(Long id){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		List<CartItem> items = cart.getItems();
		List<CartItemDTO> itemData = new ArrayList<CartItemDTO>();
		
		for(CartItem item : items) {
			Optional<Product> product = productRepo.findById(item.getIdProduct());
			if(product.isPresent()) {
				itemData.add(new CartItemDTO(product.get(), item.getNum()));
			}
			
		}
		
		return itemData;
	}
	
	public void addItemCart(Long id, Long idProduct, int num){
		//Obtenemos el carro
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());

    	
		Optional<CartItem> existingItem = cart.getItems().stream().
				filter(item -> item.getIdProduct().equals(idProduct)).findFirst();
		
		if(existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setNum(num + item.getNum());
		}
		else {
			CartItem newCartItem = new CartItem();
			newCartItem.setNum(num);
			newCartItem.setIdProduct(idProduct);
			newCartItem.setCart(cart);
			cart.addItem(newCartItem);
		}
				
		cartRepo.save(cart);
	}
	
	public boolean updateOrDeleteProductCart(Long id, Long idProduct, int num){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		
		boolean deleted = false;
		
		Optional<CartItem> existingItem = cart.getItems().stream().
				filter(item -> item.getIdProduct().equals(idProduct)).findFirst();
		
		if(existingItem.isPresent()) {
			CartItem item = existingItem.get();
			if(num == item.getNum()) {
				//Borramos el item
	            cart.getItems().remove(item);
	            cartItemRepo.delete(item);
	            deleted = true;
			}
			else {
				item.setNum(item.getNum() - num);
			}
		}
				
		cartRepo.save(cart);
		
		return deleted;
	}
	
}
