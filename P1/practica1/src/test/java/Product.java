

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Entity
@Data
public class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nombre;
	private double precio;
	public Product() {
	 }
	
	public Product copy() {
		Product p = new Product();
	    p.setNombre(getNombre());
	    p.setPrecio(getPrecio());
	    return p;
	}
   
}
