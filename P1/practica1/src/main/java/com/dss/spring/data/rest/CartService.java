package com.dss.spring.data.rest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;
	
	public CartService(CartRepo cartRepo, ProductRepo productRepo) {
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
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
	
	public boolean deleteProductCart(Long id, Long idProduct, int num){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		
		CartItem newCartItem = new CartItem();
		newCartItem.setNum(num);
		newCartItem.setIdProduct(idProduct);
		
		boolean deleted = cart.deleteItem(newCartItem);
		
		cartRepo.save(cart);
		
		return deleted;
	}
	
}
