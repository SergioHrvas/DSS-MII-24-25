// Función para cargar productos con autenticación básica
async function loadProducts() {
    const username = 'admin'; // Cambia esto por tu nombre de usuario
    const password = 'admin'; // Cambia esto por tu contraseña

    // Codificar las credenciales en Base64
    const credentials = btoa(`${username}:${password}`);

	    try {
        const response = await fetch('/api/products', {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
            }
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
				  		<input type="number" min="1" class="form-control ms-2" style="max-width: 100px;">
				  	<a href="editar-producto/${product.id}" class="btn btn-warning ms-2">Editar</a>
                   <button onClick="deleteProduct(${product.id})" class="btn btn-danger ms-2">Eliminar</button>
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
async function deleteProduct(id){
	const username = 'admin'; // Cambia esto por tu nombre de usuario
	const password = 'admin'; // Cambia esto por tu contraseña

	// Codificar las credenciales en Base64
	const credentials = btoa(`${username}:${password}`);

		try {
	    const response = await fetch('/api/products/' + id, {
	        method: 'DELETE',
	        headers: {
	            'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
	        }
	    });
		
		location.reload()
	} catch (error) {
	    console.error('Error deleting product:', error);
	}
}


//Función para eliminar un producto
async function addProduct(idProduct, num){
	const username = 'admin'; // Cambia esto por tu nombre de usuario
	const password = 'admin'; // Cambia esto por tu contraseña

	console.log(num);
	// Codificar las credenciales en Base64
	const credentials = btoa(`${username}:${password}`);

	json_body = JSON.stringify({
		idProduct,
		num
	})
		try {
	    const response = await fetch('/api/cart/1', {
	        method: 'POST',
	        headers: {
				'Content-Type': 'application/json', // Asegúrate de establecer el tipo de contenido
	            'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
	        },
			body: json_body
	    });
		
		location.reload()
	} catch (error) {
	    console.error('Error deleting product:', error);
	}
}


// Cargar productos al cargar la página
window.onload = loadProducts;