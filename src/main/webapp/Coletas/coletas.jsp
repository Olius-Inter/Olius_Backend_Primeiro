<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <!-- Define o idioma e a forma como os textos aparecem na página. Também carrega os ícones, a fonte Poppins e os arquivos CSS que cuidam da aparência da tela. -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/favicon-16x16.png">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="manifest" href="${pageContext.request.contextPath}/images/site.webmanifest">
    <title>Coletas realizadas - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/coletas.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
</head>

<!-- Guarda as informações que o JavaScript usa para buscar as solicitações de coleta e mostrar seus dados na tabela. Também define que essa tela só pode ser usada para consulta e que devem aparecer apenas as coletas com status "Realizada". -->
<body data-api="${pageContext.request.contextPath}/solicitacoes-coleta" data-id="id_solicitacao" data-base="${pageContext.request.contextPath}/Coletas" data-columns="id_b2b,dt_solicitacao,litros_estimados,status" data-readonly="true" data-status="Realizada" data-filtro-status="false">


<!-- Menu lateral com os links para acessar as outras páginas do sistema. A classe ativo destaca a opção Coletas realizadas, indicando em qual página o usuário está. -->
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
        <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp" class="ativo">Coletas realizadas</a>
        <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp">Certificações</a>
        <a href="${pageContext.request.contextPath}/login-adm?sair=1">Sair</a>
        </nav>
</aside>

<!-- Parte principal da página, onde aparece o título "Coletas realizadas" e ficam as informações sobre as coletas que já foram concluídas. -->
<main class="conteudo">

    <header class="topo">
        <h1>Coletas realizadas</h1>
    </header>

         <!-- Área de busca que permite encontrar uma coleta específica na lista e limpar os filtros aplicados, facilitando a localização das informações. -->
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

        <!-- Tabela que mostra as coletas já realizadas, apresentando o nome da empresa solicitante, a data da solicitação, o volume estimado de óleo e o status da coleta. Os dados são carregados pelo JavaScript. -->
        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">
        <table>
            <thead>
            <tr>
                <th>Empresa solicitante</th>
                <th>Data da solicitação</th>
                <th>Volume estimado</th>
                <th>Status</th>
            </tr>
            </thead>

            <tbody></tbody>
        </table>
    </section>

</main>

<!-- Carrega os arquivos JavaScript responsáveis pelas funções da listagem e pelo funcionamento do menu lateral. O atributo defer faz com que os scripts sejam executados depois da leitura do HTML. -->
    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
