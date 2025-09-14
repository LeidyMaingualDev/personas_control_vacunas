const apiUrl = "http://localhost:8080/auth";

// Cambiar entre login y registro
document.getElementById("showRegister").addEventListener("click", (e) => {
  e.preventDefault();
  document.getElementById("loginBox").classList.add("hidden");
  document.getElementById("registerBox").classList.remove("hidden");
  document.getElementById("resultado").innerText = "";
});

document.getElementById("showLogin").addEventListener("click", (e) => {
  e.preventDefault();
  document.getElementById("registerBox").classList.add("hidden");
  document.getElementById("loginBox").classList.remove("hidden");
  document.getElementById("resultado").innerText = "";
});

// LOGIN
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("loginEmail").value;
  const password = document.getElementById("loginPassword").value;

  try {
    const response = await fetch(`${apiUrl}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    const data = await response.json();
    console.log("Respuesta login:", response.status, data);

    if (!response.ok) {
      throw new Error(data.message || "Credenciales incorrectas");
    }

    if (data.token) {
      localStorage.setItem("token", data.token);
    }

    document.getElementById("resultado").innerText = "Login exitoso ✅";
  } catch (error) {
    console.error("Error en login:", error);
    document.getElementById("resultado").innerText = "Error: " + error.message;
  }
});

// REGISTRO
document.getElementById("registerForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const name = document.getElementById("regName").value;
  const email = document.getElementById("regEmail").value;
  const password = document.getElementById("regPassword").value;
  const role = document.getElementById("regRole").value;

  try {
    const response = await fetch(`${apiUrl}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password, role })
    });

    const data = await response.json();
    console.log("Respuesta registro:", response.status, data);

    if (!response.ok) {
      throw new Error(data.message || "No se pudo registrar");
    }

    document.getElementById("resultado").innerText = "Registro exitoso 🎉";
    // volver al login después de registrar
    document.getElementById("registerBox").classList.add("hidden");
    document.getElementById("loginBox").classList.remove("hidden");
  } catch (error) {
    console.error("Error en registro:", error);
    document.getElementById("resultado").innerText = "Error: " + error.message;
  }
});