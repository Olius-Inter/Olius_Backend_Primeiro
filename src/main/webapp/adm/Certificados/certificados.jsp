<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <!-- Define o idioma e a forma de como os textos aparecem na página. Tambem carraga os icones, as fontes e os arquivos CSS que cuidam da aparencia da tela   -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/favicon-16x16.png">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="manifest" href="${pageContext.request.contextPath}/images/site.webmanifest">
    <title>Certificações - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/certificados.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
</head>
<body data-api="${pageContext.request.contextPath}/certificado" data-id="id_certificado" data-base="${pageContext.request.contextPath}/Certificados" data-columns="id_usuario_b2b,dt_emissao,volume_total,arquivo_pdf">

    <!-- Menu lateral com os links que o JS usa para encontrar os dados das certificações, identificar registros e saber quais informações serão mostradas -->
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
            <a href="${pageContext.request.contextPath}/adm/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp" class="ativo">Certificações</a>
            <a href="${pageContext.request.contextPath}/login-adm?sair=1">Sair</a>
        </nav>
    </aside>

    <!-- Parte principal da página, onde aparece o titulo o botao para adicionar uma nova certificação-->
    <main class="conteudo">

        <header class="topo">
            <h1>Certificações</h1>
            <button class="adicionar" type="button" data-adicionar>+ Adicionar Certificação</button>
        </header>

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

        <!-- Tabela que mostra as certificações cadastradas, com o nome da empresa, data de emissão, volume total e o arquivo PDF-->
        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">
            <table>
                <thead>
                    <tr>
                        <th>Empresa</th>
                        <th>Data de emissão</th>
                        <th>Volume total</th>
                        <th>Arquivo PDF</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody></tbody>
            </table>
        </section>

    </main>


    <!-- Adiciona o formulario de cadastro e edição dentro da propria pagina de certificações -->
    <%@ include file="formulario.jsp" %>

    <!-- excluir somente depois da confirmacao -->
    <%@ include file="excluir.jsp" %>

    <!-- Carrega os arquivos JS que cuidam das validações, da edição dos registros e funcionamento do menu -->
    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
