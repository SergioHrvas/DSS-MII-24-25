// Función para cargar items del carro con autenticación básica
async function loadItems() {
    const username = 'admin'; // Cambia esto por tu nombre de usuario
    const password = 'admin'; // Cambia esto por tu contraseña

    // Codificar las credenciales en Base64
    const credentials = btoa(`${username}:${password}`);
	    try {
        const response = await fetch('/api/cart/1', {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
            }
        });
				
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }

        const items = await response.json();
		console.log(items);

        const itemsTable = document.getElementById('items-table').querySelector('tbody');
        itemsTable.innerHTML = ''; // Limpiar la tabla

        items.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.productId}</td>
                <td>${item.productName}</td>
                <td>$${item.productPrice.toFixed(2)}</td>
				<td>$${item.productNum}</td>
				<td>$${item.productNum * item.productPrice.toFixed(2)}</td>
                <td>
                    <button onClick="deleteProduct(${item.productId})" class="btn btn-danger btn-sm">Eliminar</button>
                </td>
            `;
            itemsTable.appendChild(row);
        });
    } catch (error) {
        console.error('Error loading products:', error);
    }
}
/*

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


*/
// Cargar productos al cargar la página
window.onload = loadItems;