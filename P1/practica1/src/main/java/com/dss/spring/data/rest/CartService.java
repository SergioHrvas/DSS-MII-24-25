package com.dss.spring.data.rest;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CartService {
	private final CartRepo cartRepo;
	
	public CartService(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	public List<CartItem> getProductsCart(long id){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		return cart.getItems();
	}
	
	public void addItemCart(long id, long idProduct, int num){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		
		CartItem newCartItem = new CartItem();
		newCartItem.setNum(num);
		newCartItem.setIdProduct(idProduct);
		
		cart.addItem(newCartItem);
		
		cartRepo.save(cart);
	}
	
	public boolean deleteProductCart(Long id, long idProduct, int num){
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
		
		CartItem newCartItem = new CartItem();
		newCartItem.setNum(num);
		newCartItem.setIdProduct(idProduct);
		
		boolean deleted = cart.deleteItem(newCartItem);
		
		cartRepo.save(cart);
		
		return deleted;
	}
	
}
