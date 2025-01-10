package com.fastcart.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "users") // O "user" si quieres escaparlo
@Data
public class User implements UserDetails {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id") // Relación propietaria
	@JsonManagedReference
	private Cart cart;

	
	public void setCart(Cart cart) {
	    this.cart = cart;
	    if (cart != null) {
	        cart.setUser(this);
	    }
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

    @Override
    public String getPassword() {
        return password; // Devuelve el valor del campo "password"
    }

    @Override
    public String getUsername() {
        return username; // Devuelve el valor del campo "username"
    }

    public Role getRole() {
        return role; // Devuelve el valor del campo "username"
    }
    
    public Cart getCart() {
        return cart; // Devuelve el valor del campo "username"
    }

    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setRole(Role role) {
    	this.role = role;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
}