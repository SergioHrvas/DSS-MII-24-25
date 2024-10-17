package com.fastcart.service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
			Optional<Product> product = productRepo.findById(item.getProduct().getId());
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
		Product product = productRepo.findById(idProduct).get();
		
		
		Optional<CartItem> existingItem = cart.getItems().stream().
				filter(item -> item.getProduct().getId().equals(idProduct)).findFirst();
		
		if(existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setNum(num + item.getNum());
		}
		else {
			CartItem newCartItem = new CartItem();
			newCartItem.setNum(num);
			newCartItem.setProduct(product);
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
				filter(item -> item.getProduct().getId().equals(idProduct)).findFirst();
		
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
	
	public byte[] generateCartPdf(Authentication authentication) {
		String userName = authentication.getName();

		User user = userRepo.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException());
		Cart cart = user.getCart();
		
        // Lógica para generar PDF con los detalles del carrito
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        
        try { 

        PdfWriter pdfWriter = PdfWriter.getInstance(document, baos); //Error here        
     // Abrimos el documento
        document.open();

        // Estilos de fuente
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        // Título del documento
        Paragraph title = new Paragraph("Carrito de compras", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("Usuario: " + cart.getUser().getUsername(), normalFont));
        document.add(new Paragraph(" ")); // Espacio en blanco

        // Tabla de productos
        PdfPTable table = new PdfPTable(3); // 3 columnas: Producto, Cantidad, Precio
        table.setWidthPercentage(100); // Tabla a pantalla completa
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Encabezados
        PdfPCell header1 = new PdfPCell(new Phrase("Producto", headerFont));
        PdfPCell header2 = new PdfPCell(new Phrase("Cantidad", headerFont));
        PdfPCell header3 = new PdfPCell(new Phrase("Precio", headerFont));

        // Colores y alineación para el encabezado
        header1.setBackgroundColor(BaseColor.DARK_GRAY);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header2.setBackgroundColor(BaseColor.DARK_GRAY);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        header3.setBackgroundColor(BaseColor.DARK_GRAY);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Agregar encabezados a la tabla
        table.addCell(header1);
        table.addCell(header2);
        table.addCell(header3);
        
        // Añadimos los productos del carrito
        for (CartItem item : cart.getItems()) {
            PdfPCell productCell = new PdfPCell(new Phrase(item.getProduct().getNombre(), normalFont));
            PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(item.getNum()), normalFont));
            PdfPCell priceCell = new PdfPCell(new Phrase(String.format("%.2f €", item.getProduct().getPrecio()), normalFont));

            // Alineación de contenido
            productCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(productCell);
            table.addCell(quantityCell);
            table.addCell(priceCell);
        }

        // Añadir la tabla al documento
        document.add(table);

        // Precio total del carrito
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrecio() * item.getNum())
                .sum();
        document.add(new Paragraph("Total: " + String.format("%.2f", totalPrice) + " €", titleFont));

        // Cerrar el documento
        document.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
        return baos.toByteArray();

	}
	
}
