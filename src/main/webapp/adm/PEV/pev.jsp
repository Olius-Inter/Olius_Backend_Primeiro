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

    <title>Pontos de Coleta - Olius</title>

    <!-- Arquivos de estilo e fontes usados na página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/pev.css">

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

<!-- Informações usadas pelo JavaScript para carregar e organizar os dados dos PEVs -->
<body data-endereco="true" data-api="${pageContext.request.contextPath}/pev" data-id="id_pev" data-base="${pageContext.request.contextPath}/PEV" data-columns="id_usuario,dt_aprovacao,status,endereco_completo">

    <!-- Menu lateral com os links para as outras páginas do sistema -->
    <aside class="menu">

        <div class="logo">
            <a href="${pageContext.request.contextPath}/adm/index.jsp" title="Ir para a tela inicial"><img src="${pageContext.request.contextPath}/images/logo.png.png" alt="Logo Olius"></a>
        </div>

         <div class="caixa"></div>
        <button type="button" class="botao-menu" aria-expanded="false" aria-controls="menuNavegacao">Menu</button>
        <nav id="menuNavegacao" aria-label="Menu principal">
            <a href="${pageContext.request.contextPath}/adm/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adm/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/adm/PEV/pev.jsp" class="ativo">PEV</a>
            <a href="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp">Certificações</a>
            <a href="${pageContext.request.contextPath}/login-adm?sair=1">Sair</a>
        </nav>

    </aside>

     <!-- Área principal da página, com o título e o botão para adicionar um PEV -->
    <main class="conteudo">

        <header class="topo">
            <h1>Pontos de coleta (PEV)</h1>

            <button type="button" class="adicionar" data-adicionar>
                + Adicionar PEV
            </button>
        </header>

        <!-- Campo de busca e filtros para encontrar registros na tabela -->
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

        <!-- Tabela que mostra os pontos de coleta cadastrados -->
        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">

            <table>
                <thead>
                    <tr>
                        <th>Responsável pelo ponto</th>
                        <th>Data de aprovação</th>
                        <th>Status</th>
                        <th>Endereço</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody></tbody>
            </table>

        </section>

    </main>


    <!-- Inclui o formulário usado para adicionar e editar pontos de coleta -->
    <%@ include file="formulario.jsp" %>

     <!-- Inclui a janela que pede confirmação antes de excluir um registro -->
    <%@ include file="excluir.jsp" %>

    <!-- Arquivos JavaScript responsáveis pelas ações da página -->
    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
