<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/favicon-16x16.png">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="manifest" href="${pageContext.request.contextPath}/images/site.webmanifest">

    <title>Solicitações de coleta - Olius</title>

    <!-- Arquivos de estilo, fontes e ícones usados na página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/solicitacoes.css">

    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap"
        rel="stylesheet"
    >

    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
</head>

<!-- Informações usadas pelo JavaScript para buscar e organizar as solicitações -->
<body data-api="${pageContext.request.contextPath}/solicitacoes-coleta" data-id="id_solicitacao" data-base="${pageContext.request.contextPath}/Solicitações" data-columns="id_b2b,dt_solicitacao,litros_estimados,status">

    <!-- Menu lateral com acesso às outras páginas do sistema -->
    <aside class="menu">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png.png" alt="Logo Olius">
        </div>

        <div class="caixa"></div>

        <button type="button" class="botao-menu" aria-expanded="false" aria-controls="menuNavegacao">Menu</button>
        <nav id="menuNavegacao" aria-label="Menu principal">
            <a href="${pageContext.request.contextPath}/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp" class="ativo">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificações</a>
        </nav>

    </aside>

    <!-- Área principal da página, com o título e o botão de cadastro -->
    <main class="conteudo">

        <header class="topo">
            <h1>Solicitações de coleta</h1>

            <button type="button" class="adicionar" data-adicionar>
                + Adicionar Solicitação
            </button>
        </header>

         <!-- Campo de busca e filtros para facilitar a localização das solicitações -->
        <section class="filtros" aria-label="Filtros da listagem">
            <div>
                <label for="busca">Buscar nesta lista</label>
                <input type="search" id="busca" placeholder="Digite para buscar" autocomplete="off">
            </div>
            <div id="filtroCampoContainer" hidden>
                <label for="filtroCampo" id="rotuloFiltro">Status</label>
                <select id="filtroCampo"><option value="">Todos</option></select>
            </div>
            <button type="button" id="limparFiltros">Limpar filtros</button>
        </section>

        <!-- Tabela que apresenta as solicitações cadastradas no sistema -->
        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">

            <table>
                <thead>
                    <tr>
                        <th>Empresa solicitante</th>
                        <th>Data da solicitação</th>
                        <th>Volume estimado</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody></tbody>
            </table>

        </section>

    </main>


    <!-- Inclui o formulário usado para adicionar e editar solicitações -->
    <%@ include file="formulario.jsp" %>

     <!-- Inclui a janela de confirmação antes de excluir uma solicitação -->
    <%@ include file="excluir.jsp" %>

    <!-- Arquivos JavaScript responsáveis pela validação, edição e menu -->
    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
