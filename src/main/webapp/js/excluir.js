// Localiza os botões da tela e recupera o ID do registro selecionado para exclusão.
const excluir = document.querySelector("button.excluir");
const cancelar = document.querySelector("button.cancelar");
excluir.dataset.id = new URLSearchParams(location.search).get("id") || "";
if (!/^[1-9]\d*$/.test(excluir.dataset.id)) {
    excluir.disabled = true;
    mostrarErro("Selecione um registro válido na listagem.");
}

// Ao cancelar a operação, retorna para a página indicada no botão.
cancelar.addEventListener("click", () => {
    location.href = cancelar.dataset.destino;
});

// Confere se o registro é válido e envia o pedido de exclusão ao servidor.
// Se a operação funcionar, retorna para a listagem. Caso contrário, apresenta uma mensagem de erro.
excluir.addEventListener("click", async () => {
    if (excluir.disabled) return;
    if (!/^[1-9]\d*$/.test(excluir.dataset.id)) {
        mostrarErro("Selecione um registro válido na listagem.");
        return;
    }
    excluir.disabled = true;
    try {
        const destino = new URL(excluir.dataset.url, location.origin);
        destino.searchParams.set(excluir.dataset.parametro, excluir.dataset.id);
        const resposta = await fetch(destino, { method: "DELETE" });
        const resultado = await resposta.json();
        if (!resposta.ok) throw new Error(resultado.erro || "Não foi possível excluir.");
        location.href = cancelar.dataset.destino;
    } catch (erro) {
        mostrarErro(erro.message);
        excluir.disabled = false;
    }
});

// Cria um espaço para exibir mensagens de erro na tela e mostra o texto recebido.
function mostrarErro(texto) {
    let aviso = document.getElementById("erroExclusao");
    if (!aviso) {
        aviso = document.createElement("p");
        aviso.id = "erroExclusao";
        aviso.className = "aviso";
        aviso.setAttribute("role", "alert");
        document.querySelector(".botoes").before(aviso);
    }
    aviso.textContent = texto;
}