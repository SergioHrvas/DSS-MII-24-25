
var pathArray = window.location.pathname.split('/');
var modo = pathArray[1] == "nuevo-producto" ? 0 : 1;

if(modo == 0){
	// Cargar productos al cargar la página
	window.onload = loadProducts;
}
else{
	document.getElementById("productTable").innerHTML = ""
}
// Función para verificar si un valor es un ID válido
function isValidId(id) {
    return /^\d+$/.test(id); // Verifica si el ID solo contiene dígitos
}

document.addEventListener("DOMContentLoaded", function() {
	// Extraer parámetros de la URL

	const path = window.location.pathname; // Obtiene la ruta completa
	const productId = path.split('/').pop(); // Toma el último segmento de la ruta


	if (isValidId(productId)) {
		document.getElementById("title").innerText = "Modificar producto";
		document.getElementById("description").innerText = "";
		document.getElementById("botonagregar").innerText = "Modificar Producto";

		cargarDatosProducto(productId);
	}
})

async function cargarDatosProducto(id) {
	try {

		const response = await fetch('/api/products/' + id, {
			method: 'GET',
		});

				if (response.ok) {
			const product = await response.json();
			document.getElementById("nombre").value = product.nombre;
			document.getElementById("precio").value = product.precio;
		} else {
			alert("No se pudo cargar el producto");
		}
	} catch (error) {
		console.error("Error al cargar producto:", error);
		alert("Error de conexión");
	}
}


async function saveProduct(event) {
	event.preventDefault(); // Evitar que el formulario se envíe de manera tradicional

	// Extraer parámetros de la URL
	const path = window.location.pathname; // Obtiene la ruta completa
	const productId = path.split('/').pop(); // Toma el último segmento de la ruta

	const nombre = document.getElementById('nombre').value;
	const precio = document.getElementById('precio').value;
	
	var json_body;
	if(isValidId(productId)){
		json_body = JSON.stringify({"id": productId, nombre, precio})
	} else{
		json_body = JSON.stringify({nombre, precio})
	}
	try {

			const response = await fetch('/admin/api/products', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: json_body
			}).
				then(response => response.json().
					then(data => {
					    window.location.href = '/nuevo-producto';}
					).
					catch(error => console.error("Error: " + error)));
	} catch (error) {
		console.error('Error creating product:', error);
	}
}

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

		try {
	    const response = await fetch('/admin/api/products/' + id, {
	        method: 'DELETE',
	    });
		
		location.reload()
	} catch (error) {
	    console.error('Error deleting product:', error);
	}
}
