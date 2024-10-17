// Manejar el envío del formulario
       document.getElementById('registerForm').addEventListener('submit', function(event) {
           event.preventDefault(); // Prevenir el envío normal del formulario

           const formData = new FormData(this);
           const data = Object.fromEntries(formData.entries());

           fetch('/api/user/register', {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json'
               },
               body: JSON.stringify(data)
           })
           .then(response => {
               if (!response.ok) {
                   return response.json().then(errorData => {
                       throw new Error(errorData.message || 'Error al registrar el usuario');
                   });
               }
               return response.json();
           })
           .then(data => {
			window.location.href = '/';

           })
           .catch(error => {
               document.getElementById('errorMessage').textContent = error.message;
               document.getElementById('errorMessage').style.display = 'block';
           });
       });