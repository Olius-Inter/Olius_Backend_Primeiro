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

                    <%-- o LoginAdmServlet devolve para esta página com "erro" e "emailDigitado" --%>
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
                        <div class="campo-senha">
                            <input class="campo" type="password" id="senha" name="senha" autocomplete="current-password"
                                   placeholder="Digite sua senha" required>
                            <%-- botão do olho: mostra ou esconde a senha digitada (só no navegador, nada é enviado) --%>
                            <button class="ver-senha" type="button" id="verSenha"
                                    aria-label="Mostrar senha" aria-pressed="false" title="Mostrar senha">
                                <svg class="olho-aberto" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                     stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                                    <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"/><circle cx="12" cy="12" r="3"/>
                                </svg>
                                <svg class="olho-fechado" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor"
                                     stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                                    <path d="M17.94 17.94A10.9 10.9 0 0 1 12 19C5 19 1 12 1 12a18.5 18.5 0 0 1 5.06-5.94"/>
                                    <path d="M9.9 4.24A10.9 10.9 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>
                                    <path d="M14.12 14.12a3 3 0 1 1-4.24-4.24"/><path d="M1 1l22 22"/>
                                </svg>
                            </button>
                        </div>

                        <div class="botoes">
                            <button class="botao cancelar" type="reset">Cancelar</button>
                            <button class="botao entrar" type="submit">Entrar</button>
                        </div>
                    </form>

                    <% if (erro != null) { %>
                        <p class="alerta" role="alert"><%= escapar(erro) %></p>
                    <% } else { %>
                        <p class="aviso">Somente administradores cadastrados e ativos no sistema da Olius conseguem acessar o painel.</p>
                    <% } %>
                </div>

                <p class="voltar">Não é administrador? <a href="${pageContext.request.contextPath}/index/index.html">Voltar ao site</a></p>
            </div>

        </section>
    </main>

    <script>
        // Botão do olho: alterna o campo de senha entre escondido e visível
        (function () {
            var campo = document.getElementById("senha");
            var botao = document.getElementById("verSenha");
            botao.addEventListener("click", function () {
                var mostrando = campo.type === "password";
                campo.type = mostrando ? "text" : "password";
                botao.classList.toggle("mostrando", mostrando);
                botao.setAttribute("aria-pressed", mostrando ? "true" : "false");
                var texto = mostrando ? "Esconder senha" : "Mostrar senha";
                botao.setAttribute("aria-label", texto);
                botao.title = texto;
                campo.focus();
            });
        })();
    </script>

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
