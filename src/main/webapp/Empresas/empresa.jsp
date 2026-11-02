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

    <title>Empresas - Olius</title>

    <!-- arquivo que cuida da aparência da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/empresas.css">

    <!-- fonte usada no restante do sistema -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <!-- biblioteca dos ícones de editar e excluir -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
</head>

<body data-endereco="true" data-api="${pageContext.request.contextPath}/empresas" data-id="id_usuario" data-base="${pageContext.request.contextPath}/Empresas" data-columns="nome_empresa,cnpj,plano,id_endereco,telefone,email">

    <!--menu que fica na lateral da tela -->
    <aside class="menu">

        <!--logo do Olius -->
        <div class="logo">
            <a href="${pageContext.request.contextPath}/adm/index.jsp" title="Ir para a tela inicial"><img src="${pageContext.request.contextPath}/images/logo.png.png" alt="Logo Olius"></a>
        </div>


        <div class="caixa"></div>

        <!--Links para acessar as outras páginas do painel -->
        <button type="button" class="botao-menu" aria-expanded="false" aria-controls="menuNavegacao">Menu</button>
        <nav id="menuNavegacao" aria-label="Menu principal">
            <a href="${pageContext.request.contextPath}/adm/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adm/Empresas/empresa.jsp" class="ativo">Empresas</a>
            <a href="${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/adm/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp">Certificações</a>
            <a href="${pageContext.request.contextPath}/login-adm?sair=1">Sair</a>
        </nav>

    </aside>

    <!-- área principal, onde ficam as informações das empresas -->
    <main class="conteudo">

        <!-- título da página e botão para cadastrar uma empresa -->
        <header class="topo">
            <h1>Empresas (B2B)</h1>

            <button
                type="button"
                class="adicionar"
                data-adicionar>
                + Adicionar Empresa
            </button>
        </header>

        <!-- tabela com os dados cadastrados -->
        <!-- filtros da listagem, sem alterar os dados cadastrados -->
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

        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">

            <table>

                <!-- nomes das colunas da tabela -->
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>CNPJ</th>
                        <th>Plano</th>
                        <th>Endereço</th>
                        <th>Telefone</th>
                        <th>E-mail</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <!-- Empresas usadas como exemplo para preencher a tabela -->
                <tbody></tbody>

            </table>

        </section>

    </main>


    <!-- cadastro e edicao na propria listagem -->
    <%@ include file="formulario.jsp" %>

    <!-- excluir somente depois da confirmacao -->
    <%@ include file="excluir.jsp" %>

    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
