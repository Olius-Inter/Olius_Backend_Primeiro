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

    <title>Empresas - Olius</title>

     <!-- Carrega os arquivos responsáveis pela aparência da página, pela fonte e pelos ícones usados nos botões do sistema. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/empresas.css">

    <!-- fonte usada no restante do sistema -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <!-- biblioteca dos ícones de editar e excluir -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
</head>

<!-- Guarda as informações que o JavaScript usa para buscar os dados das empresas, identificar cada registro e saber quais informações serão mostradas na tabela. Também indica que os endereços fazem parte do cadastro. -->
<body data-endereco="true" data-api="${pageContext.request.contextPath}/empresas" data-id="id_usuario" data-base="${pageContext.request.contextPath}/Empresas" data-columns="nome_empresa,cnpj,plano,id_endereco,telefone">

     <!-- Menu lateral com os links para acessar as outras páginas do sistema. A classe ativo destaca a opção Empresas, mostrando em qual página o usuário está. -->
    <aside class="menu">

         <!-- Mostra a logo do Olius no menu lateral. -->
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png.png" alt="Logo Olius">
        </div>


        <div class="caixa"></div>

         <!-- Reúne os links para acessar as diferentes áreas do painel, como usuários, motoristas, coletas e certificações. -->
        <button type="button" class="botao-menu" aria-expanded="false" aria-controls="menuNavegacao">Menu</button>
        <nav id="menuNavegacao" aria-label="Menu principal">
            <a href="${pageContext.request.contextPath}/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp" class="ativo">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificações</a>
        </nav>

    </aside>

   <!-- Parte principal da página, onde ficam o título da área de empresas e o botão para adicionar um novo cadastro. -->
    <main class="conteudo">

        <!-- Mostra o nome da página e o botão que abre o formulário para cadastrar uma nova empresa. -->
        <header class="topo">
            <h1>Empresas (B2B)</h1>

            <button
                type="button"
                class="adicionar"
                data-adicionar>
                + Adicionar Empresa
            </button>
        </header>

        <!-- Área de busca que permite encontrar uma empresa na lista e limpar os filtros usados. -->
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

         <!-- Tabela que mostra as empresas cadastradas e suas principais informações, como nome, CNPJ, plano, endereço e telefone. Também possui uma coluna para as ações disponíveis em cada registro. -->
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
                        <th>Ações</th>
                    </tr>
                </thead>

                <!-- Os dados das empresas são carregados pelo JavaScript e aparecem nesta parte da tabela. -->
                <tbody></tbody>

            </table>

        </section>

    </main>


    <!-- Adiciona o formulário de cadastro e edição dentro da própria página de empresas. -->
    <%@ include file="formulario.jsp" %>

    <!-- Adiciona a janela que aparece quando o usuário deseja excluir uma empresa. -->
    <%@ include file="excluir.jsp" %>
    <!-- Carrega os arquivos JavaScript que cuidam das validações, da edição dos registros e do funcionamento do menu. -->
    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

    <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
</html>
