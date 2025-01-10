package com.fastcart.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Cart{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @OneToMany(mappedBy = "cart",  cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Un carrito tiene múltiples CartItems
	private List<CartItem> items = new ArrayList<>();;
    
	@OneToOne(mappedBy = "cart")
	@JsonBackReference
	private User user;

    public List<CartItem> getItems(){
    	return items;
    }
	
	public void addItem(CartItem new_item) {
		items.add(new_item);
	}
	
	boolean deleteItem(CartItem new_item) {
		for(CartItem item : getItems()) {
			if(item.getProduct().getId() == new_item.getProduct().getId()) {
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
	
	@Override
    public String toString() {
        // Evita imprimir toda la lista de items para evitar recursión
        return "Cart{id=" + this.id + ", itemsCount=" + (items != null ? items.size() : 0) + "}";
    }
	
	public void setUser(User user) {
	    this.user = user;
	    if (user != null && user.getCart() != this) {
	        user.setCart(this);
	    }
	    
	    clearItems();
	}
	
	public void clearItems() {
		this.items.clear(); // Vaciar la lista después de procesar cada item
	}
	
	public User getUser() {
		return user;
	}
}
