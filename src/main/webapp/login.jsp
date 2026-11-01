<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Olius - Entrar</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

    <!-- CABEÇALHO: mesmo da landing -->
    <header class="header">
        <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
            <img class="logo-icon" src="${pageContext.request.contextPath}/assets/global/logo-normal.svg" alt="Logotipo Olius">
            <span class="logo-texto">Olius</span>
        </a>
    </header>

    <main class="login-main">
        <section class="login-card">
            <h1>Bem-vindo de <span class="highlight">volta.</span></h1>
            <p class="login-subtitulo">Entre na sua conta para acompanhar coletas, pontos e certificados.</p>

            <%-- mensagem de erro enviada pelo LoginSERVLET --%>
            <% if (request.getAttribute("erro") != null) { %>
                <div class="login-alerta erro" role="alert"><%= escapar((String) request.getAttribute("erro")) %></div>
            <% } else if (request.getParameter("saiu") != null) { %>
                <div class="login-alerta info" role="status">Você saiu da sua conta.</div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="post" novalidate>
                <div class="campo">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" autocomplete="username"
                           placeholder="voce@empresa.com" required autofocus
                           value="<%= escapar((String) request.getAttribute("emailDigitado")) %>">
                </div>

                <div class="campo">
                    <label for="senha">Senha</label>
                    <div class="campo-senha">
                        <input type="password" id="senha" name="senha" autocomplete="current-password"
                               placeholder="Sua senha" required>
                        <button type="button" class="btn-mostrar" id="alternarSenha" aria-controls="senha">Mostrar</button>
                    </div>
                </div>

                <button type="submit" class="btn-entrar">Entrar</button>
            </form>

            <p class="login-rodape">Ainda não tem conta? <a href="${pageContext.request.contextPath}/contato.jsp">Fale com a gente</a></p>
        </section>
    </main>

    <%!
        // escapa HTML para exibir com seguranca texto vindo do servidor/usuario
        private static String escapar(String s) {
            if (s == null) return "";
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&#39;");
        }
    %>

    <script>
        // alterna a visibilidade da senha
        (function () {
            var botao = document.getElementById("alternarSenha");
            var campo = document.getElementById("senha");
            botao.addEventListener("click", function () {
                var mostrar = campo.type === "password";
                campo.type = mostrar ? "text" : "password";
                botao.textContent = mostrar ? "Ocultar" : "Mostrar";
            });
        })();
    </script>
</body>
</html>
