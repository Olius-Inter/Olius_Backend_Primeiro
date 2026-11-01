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

    <title>Motoristas - Olius</title>

     <!-- Carrega os arquivos responsáveis pela aparência da página, pela fonte e pelos ícones usados no sistema. -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/motoristas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/motoristas.css">

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

<!-- Guarda as informações que o JavaScript usa para buscar os motoristas, identificar cada registro e saber quais dados serão mostrados na tabela. -->
<body data-api="${pageContext.request.contextPath}/motoristas" data-id="id_motorista" data-base="${pageContext.request.contextPath}/Motoristas" data-columns="nome,telefone,empresa,cnh,status">

    <!-- Menu lateral com os links para acessar as outras páginas do sistema. A classe ativo destaca a opção Motoristas, mostrando em qual página o usuário está. -->
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
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp" class="ativo">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificações</a>
        </nav>

    </aside>

     <!-- Parte principal da página, onde ficam o título da área de motoristas e o botão para adicionar um novo cadastro. -->
    <main class="conteudo">

        <header class="topo">
            <h1>Motoristas</h1>

            <button type="button" class="adicionar" data-adicionar>
                + Adicionar Motorista
            </button>
        </header>

         <!-- Área de busca que permite encontrar um motorista na lista e limpar os filtros usados. -->
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


        <!-- Tabela que mostra os motoristas cadastrados e suas informações, como nome, telefone, empresa responsável, CNH e status. Os dados são carregados pelo JavaScript. -->
        <section class="tabela-container" tabindex="0" aria-label="Registros cadastrados">

            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Telefone</th>
                        <th>Empresa responsável</th>
                        <th>Licença</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody></tbody>
            </table>

        </section>

    </main>


     <!-- Adiciona o formulário de cadastro e edição dentro da própria página de motoristas. -->
    <%@ include file="formulario.jsp" %>

    <!-- Adiciona a janela que aparece quando o usuário deseja excluir um motorista. -->
    <%@ include file="excluir.jsp" %>

      <!-- Carrega os arquivos JavaScript que cuidam das validações, da edição dos registros e do funcionamento do menu. -->
    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
