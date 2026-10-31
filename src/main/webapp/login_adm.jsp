<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    // Escapa HTML para mostrar com segurança texto vindo do servidor/usuário.
    private static String escapar(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<% String erro = (String) request.getAttribute("erro"); %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content="noindex">
    <title>Olius - Acesso administrativo</title>

    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/assets/global/logo-normal.svg">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_adm.css">
</head>
<body>

    <main class="palco" id="palco">
        <section class="cartao" id="cartao" aria-label="Acesso administrativo Olius">

            <!-- Lado visual: frase, pontos decorativos, planta e celular -->
            <div class="lado-visual" aria-hidden="true">
                <p class="frase">Olius conecta você ao que importa.</p>
                <div class="pontos"></div>
                <img class="planta" src="${pageContext.request.contextPath}/assets/login/planta.png" alt="">
                <img class="celular" src="${pageContext.request.contextPath}/assets/login/celular.png" alt="">
            </div>

            <!-- Lado do formulário -->
            <div class="lado-form">
                <img class="logo" src="${pageContext.request.contextPath}/assets/login/logo.svg" alt="Logo Olius">

                <div class="conteudo">
                    <h1>Bem-vindo, administrador!</h1>
                    <p class="subtitulo">Área restrita da equipe Olius</p>

                    <%-- o LoginAdmServlet devolve para esta página com "erro", "nomeDigitado" e "emailDigitado" --%>
                    <form action="${pageContext.request.contextPath}/login-adm" method="post">
                        <label class="so-leitor" for="nome">Nome do administrador</label>
                        <input class="campo" type="text" id="nome" name="nome" autocomplete="name"
                               placeholder="Digite seu nome" required maxlength="255"
                               value="<%= escapar((String) request.getAttribute("nomeDigitado")) %>">

                        <label class="so-leitor" for="email">E-mail do administrador</label>
                        <input class="campo" type="email" id="email" name="email" autocomplete="username"
                               placeholder="Digite seu e-mail" required
                               value="<%= escapar((String) request.getAttribute("emailDigitado")) %>">

                        <label class="so-leitor" for="senha">Senha</label>
                        <input class="campo" type="password" id="senha" name="senha" autocomplete="current-password"
                               placeholder="Digite sua senha" required>

                        <div class="botoes">
                            <button class="botao cancelar" type="reset">Cancelar</button>
                            <button class="botao entrar" type="submit">Entrar</button>
                        </div>
                    </form>

                    <% if (erro != null) { %>
                        <p class="alerta" role="alert"><%= escapar(erro) %></p>
                    <% } else { %>
                        <p class="aviso">Entre com o nome, o e-mail e a senha cadastrados. Somente administradores ativos no sistema da Olius acessam o painel.</p>
                    <% } %>
                </div>

                <p class="voltar">Não é administrador? <a href="${pageContext.request.contextPath}/index.jsp">Voltar ao site</a></p>
            </div>

        </section>
    </main>

    <script>
        // Em telas largas o cartão mantém as medidas do Figma (1289 x 706) e só encolhe para caber na janela.
        // Em telas pequenas (até 800px) o CSS troca para um layout de uma coluna.
        (function () {
            var palco = document.getElementById("palco");
            var cartao = document.getElementById("cartao");
            var LARGURA = 1289, ALTURA = 706;

            function ajustar() {
                if (window.innerWidth <= 800) {
                    cartao.style.transform = "";
                    palco.style.width = palco.style.height = "";
                    return;
                }
                var escala = Math.min(1, (window.innerWidth - 32) / LARGURA);
                cartao.style.transform = "scale(" + escala + ")";
                palco.style.width = (LARGURA * escala) + "px";
                palco.style.height = (ALTURA * escala) + "px";
            }

            window.addEventListener("resize", ajustar);
            ajustar();
        })();
    </script>
</body>
</html>
