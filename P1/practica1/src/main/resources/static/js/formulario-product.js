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
	const username = 'admin'; // Cambia esto por tu nombre de usuario
	const password = 'admin'; // Cambia esto por tu contraseña

	// Codificar las credenciales en Base64
	const credentials = btoa(`${username}:${password}`);

	try {

		const response = await fetch('/api/products/' + id, {
			method: 'GET',
			headers: {
				'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
			},
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


	const username = 'admin'; // Cambia esto por tu nombre de usuario
	const password = 'admin'; // Cambia esto por tu contraseña

	// Codificar las credenciales en Base64
	const credentials = btoa(`${username}:${password}`);

	const nombre = document.getElementById('nombre').value;
	const precio = document.getElementById('precio').value;
	
	var json_body;
	if(isValidId(productId)){
		json_body = JSON.stringify({"id": productId, nombre, precio})
	} else{
		json_body = JSON.stringify({nombre, precio})
	}
	try {

			const response = await fetch('/api/products', {
				method: 'POST',
				headers: {
					'Authorization': `Basic ${credentials}`, // Añadir el encabezado de autorización
					'Content-Type': 'application/json'
				},
				body: json_body
			}).
				then(response => response.json().
					then(data => {
						console.log("Producto creado", data)
					    window.location.href = '/productos';}
					).
					catch(error => console.log("Error: " + error)));
	} catch (error) {
		console.error('Error creating product:', error);
	}
}

