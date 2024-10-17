changeNav()

function changeNav() {
	let x = document.JSESSIONID;
	if (x = undefined) {
		document.getElementById("log").innerHTML = '<a class="nav-link" href="login">Iniciar sesión</a>'
	}
	else{
		document.getElementById("log").innerHTML = '<a class="nav-link" href="logout">Cerrar sesión</a>'

	}
}