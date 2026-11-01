<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Empresas - Olius</title>

    <!-- arquivo que cuida da aparência da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/empresas.css">

    <!-- fonte usada no restante do sistema -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <!-- biblioteca dos ícones de editar e excluir -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body data-api="${pageContext.request.contextPath}/empresas" data-id="id_usuario" data-base="${pageContext.request.contextPath}/adm/Empresas" data-columns="nome_fantasia,cnpj,razao_social,id_endereco,telefone,id_usuario">

    <!--menu que fica na lateral da tela -->
    <aside class="menu">

        <!--logo do Olius -->
        <div class="logo">
            <img src="${pageContext.request.contextPath}/adm/logo.png" alt="Logo Olius">
        </div>


        <div class="caixa"></div>

        <!--Links para acessar as outras páginas do painel -->
        <nav>
            <a href="${pageContext.request.contextPath}/adm/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adm/Empresas/empresa.jsp" class="ativo">Empresas</a>
            <a href="${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/adm/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">Solicitacoes de coleta</a>
            <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp">Certificados</a>
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
                onclick="window.location.href='${pageContext.request.contextPath}/adm/Empresas/formulario.jsp'">
                + Adicionar Empresa
            </button>
        </header>

        <!-- tabela com os dados cadastrados -->
        <section class="tabela-container">

            <table>

                <!-- nomes das colunas da tabela -->
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>CNPJ</th>
                        <th>Razão social</th>
                        <th>Endereço</th>
                        <th>Telefone</th>
                        <th>Usuário (ID)</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <!-- Empresas usadas como exemplo para preencher a tabela -->
                <tbody>

                   <c:forEach var="empresa" items = "${empresas}">
                       <tr>
                            <td>#<c:out value = "${empresa.nome_fantasia}"/> </td>
                            <td>#<c:out value = "${empresa.cnpj}"/> </td>
                            <td>#<c:out value = "${empresa.razao_social}"/> </td>
                            <td>#<c:out value = "${empresa.id_endereco}"/> </td>
                            <td>#<c:out value = "${empresa.telefone}"/> </td>
                            <td>#<c:out value = "${empresa.id_usuario}"/> </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/adm/Empresas/formulario.jsp?id=${empresa.id_usuario}" class="botao-editar">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <button type="button" class="excluir" title="Excluir"
                                       onclick="window.location.href='${pageContext.request.contextPath}/adm/Empresas/excluir.jsp?id=${empresa.id_usuario}'">
                                   <i class="fas fa-trash"></i>
                               </button>
                            </td>
                       </tr>
                   </c:forEach>


                </tbody>

            </table>

        </section>

    </main>

    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

</body>
</html>
