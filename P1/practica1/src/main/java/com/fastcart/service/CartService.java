package com.fastcart.service;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fastcart.dto.CartItemDTO;
import com.fastcart.repository.CartItemRepo;
import com.fastcart.model.Cart;
import com.fastcart.model.CartItem;
import com.fastcart.model.Product;
import com.fastcart.model.User;
import com.fastcart.repository.CartRepo;
import com.fastcart.repository.ProductRepo;
import com.fastcart.repository.UserRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
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
	
	public byte[] generateCartPdf(Long cartId) {
        Cart cart = cartRepo.getById(cartId);
        // Lógica para generar PDF con los detalles del carrito
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Carrito de compras"));
            document.add(new Paragraph("ID del carrito: " + cart.getId()));
            document.add(new Paragraph("Productos:"));

            for (CartItem item : cart.getItems()) {
                document.add(new Paragraph("Producto: " + item.getIdProduct()));
                document.add(new Paragraph("Cantidad: " + item.getNum()));
                document.add(new Paragraph("Precio: " + "SSDSFFD"));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }
	
}
