package com.dss.spring.data.rest;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;


import lombok.Data;

@Entity
@Data
public class Cart{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @OneToMany(mappedBy = "cart") // Un carrito tiene múltiples CartItems
	private List<CartItem> items;
	
	void addItem(CartItem new_item) {
		boolean entro = false;
		for(CartItem item : getItems()) {
			if(item.getIdProduct() == new_item.getIdProduct()) {
				item.setNum(item.getNum() + new_item.getNum());
				entro = true;
				break;
			}
		}
		if(!entro) {
			items.add(new_item);
		}
		
		
	}
	
	boolean deleteItem(CartItem new_item) {
		for(CartItem item : getItems()) {
			if(item.getIdProduct() == new_item.getIdProduct()) {
				if(item.getNum() - new_item.getNum() < 0) {
					items.remove(item);
				}
				else {
					item.setNum(item.getNum() - new_item.getNum());
				}
				return true;
			}
		}
		
		return false;
		
	}
	
}
