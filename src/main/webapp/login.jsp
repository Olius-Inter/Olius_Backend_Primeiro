<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Olius - Entrar</title>
    <link rel="icon"
          type="image/svg+xml"
          href="${pageContext.request.contextPath}/assets/global/logo-normal.svg">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

    <main class="login-main">
        <!-- Lado visual: apresentação e imagem real do aplicativo no celular -->
        <section class="login-intro" aria-label="Apresentação da plataforma Olius">
            <div class="login-intro-conteudo">
                <h1>Olius conecta<br>você ao que<br><span>importa.</span></h1>
                <p>Uma forma simples e inteligente de transformar o descarte de óleo em impacto positivo.</p>
            </div>

            <div class="login-pontos-decorativos" aria-hidden="true"></div>

            <img
                class="login-celular"
                src="${pageContext.request.contextPath}/assets/global/mock-mob.png"
                alt="Aplicativo Olius aberto em um celular, mostrando indicadores de sustentabilidade">
        </section>

        <!-- Formulário de acesso: mantém a integração com LoginSERVLET -->
        <section class="login-form-area" aria-label="Acesso à conta">
            <div class="login-form-wrap">
                <a href="${pageContext.request.contextPath}/index.jsp" class="login-brand">
                    <img src="${pageContext.request.contextPath}/assets/global/logo-normal.svg" alt="">
                    <span>Olius</span>
                </a>

                <div class="login-card">
                    <h2>Bem-vindo de <span class="highlight">volta.</span></h2>
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
                </div>
            </div>
        </section>
    </main>

    <%!
        // Escapa HTML para exibir com segurança texto vindo do servidor/usuário.
        private static String escapar(String s) {
            if (s == null) return "";
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&#39;");
        }
    %>

    <script>
        // Alterna a visibilidade da senha.
        (function () {
            var botao = document.getElementById("alternarSenha");
            var campo = document.getElementById("senha");

            if (botao && campo) {
                botao.addEventListener("click", function () {
                    var mostrar = campo.type === "password";
                    campo.type = mostrar ? "text" : "password";
                    botao.textContent = mostrar ? "Ocultar" : "Mostrar";
                });
            }
        })();
    </script>
</body>
</html>
