package com.dss.spring.data.rest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;
	private final CartItemRepo cartItemRepo;
	private final UserRepo userRepo;

	public CartService(CartRepo cartRepo, ProductRepo productRepo, CartItemRepo cartItemRepo, UserRepo userRepo) {
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartItemRepo = cartItemRepo;
		this.userRepo = userRepo;
	}
	
	public List<CartItemDTO> getProductsCart(Authentication authentication){
		String userName = authentication.getName();
		System.out.println(userName);
		User user = userRepo.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException());
		Cart cart = user.getCart();
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
	
	public void addItemCart(Authentication authentication, Long idProduct, int num){
		String userName = authentication.getName();
		
		User user = userRepo.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException());
		Cart cart = user.getCart();

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
	
	public boolean updateOrDeleteProductCart(Authentication authentication, Long idProduct, int num){
		String userName = authentication.getName();

		User user = userRepo.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException());
		Cart cart = user.getCart();

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
