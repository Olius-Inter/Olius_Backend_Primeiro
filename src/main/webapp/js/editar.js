const formulario = document.querySelector('form[method="post"]');
const salvar = formulario.querySelector('button[type="submit"]');

formulario.addEventListener("submit", async (evento) => {
    evento.preventDefault();
    const destino = new URL(formulario.action);
    // Os servlets existentes leem os parâmetros do PUT com getParameter.
    destino.search = new URLSearchParams(new FormData(formulario)).toString();
    salvar.disabled = true;
    try {
        const resposta = await fetch(destino, { method: "PUT" });
        const resultado = await resposta.json();
        if (!resposta.ok) throw new Error(resultado.erro || "Não foi possível salvar.");
        location.href = document.querySelector(".botao-cancelar").href;
    } catch (erro) {
        let aviso = document.getElementById("erroEdicao");
        if (!aviso) {
            aviso = document.createElement("p");
            aviso.id = "erroEdicao";
            aviso.className = "aviso";
            aviso.setAttribute("role", "alert");
            formulario.before(aviso);
        }
        aviso.textContent = erro.message;
        salvar.disabled = false;
    }
});