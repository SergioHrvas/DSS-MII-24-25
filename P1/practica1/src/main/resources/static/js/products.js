

// Función para cargar productos con autenticación básica
async function loadProducts() {
	try {
		const response = await fetch('/api/products', {
			method: 'GET',
		});

		if (!response.ok) {
			throw new Error(`Error: ${response.status}`);
		}

		const products = await response.json();
		const productTable = document.getElementById('productTable').querySelector('tbody');
		productTable.innerHTML = ''; // Limpiar la tabla

		products.forEach(product => {
			const row = document.createElement('tr');
			row.innerHTML = `
                <td>${product.id}</td>
                <td>${product.nombre}</td>
                <td>$${product.precio.toFixed(2)}</td>
                <td>
				<div class="input-group">
				    	<button onClick="addProduct(${product.id}, this.nextElementSibling.value)" type="button" class="btn btn-success">Añadir</button>
				  		<input type="number" value="1" min="1" class="form-control ms-2" style="max-width: 100px;">
                 </div>
				 </td>
            `;
			productTable.appendChild(row);

		});
	} catch (error) {
		console.error('Error loading products:', error);
	}
}

//Función para eliminar un producto
async function deleteProduct(id) {

	try {
		const response = await fetch('/api/products/' + id, {
			method: 'DELETE',
		});

		location.reload()
	} catch (error) {
		console.error('Error deleting product:', error);
	}
}


//Función para añadir  un producto al carro
async function addProduct(idProduct, num) {
	json_body = JSON.stringify({
		idProduct,
		num
	})
	try {
		const response = await fetch(`/api/cart`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json', // Asegúrate de establecer el tipo de contenido
			},
			body: json_body
		});
		
		if(response.url == "http://localhost:8080/login"){
			window.location.replace("/login");
		}
		else{
			location.reload()
		}
	} catch (error) {
		console.error('Error deleting product:', error);
	}
}

// Cargar productos al cargar la página
window.onload = loadProducts;
