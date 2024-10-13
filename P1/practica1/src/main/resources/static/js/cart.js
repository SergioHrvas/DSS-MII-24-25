// Función para cargar items del carro con autenticación básica
async function loadItems() {

	    try {
        const response = await fetch(`/api/cart`, {
            method: 'GET',

        });
				
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }

        const items = await response.json();

        const itemsTable = document.getElementById('items-table').querySelector('tbody');
        itemsTable.innerHTML = ''; // Limpiar la tabla
		
		var sum = 0;
		
        items.forEach(item => {
			sum += item.productNum * item.productPrice.toFixed(2);
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.productId}</td>
                <td>${item.productName}</td>
                <td>$${item.productPrice.toFixed(2)}</td>
				<td>${item.productNum}</td>
				<td>$${(item.productNum * item.productPrice).toFixed(2)}</td>
                <td>
                    <button onClick='deleteItem(${item.productId}, ${item.productNum})' class="btn btn-danger btn-sm">Eliminar</button>
                </td>
            `;
            itemsTable.appendChild(row);
			document.getElementById("cartTotal").innerHTML = "$ " + sum.toFixed(2);
        });
    } catch (error) {
        console.error('Error loading products:', error);
    }
}


//Función para eliminar un item
async function deleteItem(idProduct, num){
	const username = 'admin'; // Cambia esto por tu nombre de usuario
	const password = 'admin'; // Cambia esto por tu contraseña

	// Codificar las credenciales en Base64
	const credentials = btoa(`${username}:${password}`);


	try {
	    const response = await fetch(`/api/cart?idProduct=${idProduct}&num=${num}`, {
	        method: 'DELETE',
	        headers: {
	            'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
	        },
	    });
		
		location.reload()
	} catch (error) {
	    console.error('Error deleting item:', error);
	}
}

// Cargar productos al cargar la página
window.onload = loadItems;