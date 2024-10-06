// Función para cargar productos con autenticación básica
async function loadProducts() {
    const username = 'admin'; // Cambia esto por tu nombre de usuario
    const password = 'admin'; // Cambia esto por tu contraseña

    // Codificar las credenciales en Base64
    const credentials = btoa(`${username}:${password}`);
	console.log("aaa");
    try {
        const response = await fetch('/api/products', {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
            }
        });
		
		console.log(response);
		
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
                    <a href="editar-producto/${product.id}" class="btn btn-warning btn-sm">Editar</a>
                    <button onClick="deleteProduct(${product.id})" class="btn btn-danger btn-sm">Eliminar</button>
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
	console.log("aaa");
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



// Cargar productos al cargar la página
window.onload = loadProducts;