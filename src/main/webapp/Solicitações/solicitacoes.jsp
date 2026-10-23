<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Solicitações de coleta - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/solicitacoes.css">

    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap"
        rel="stylesheet"
    >

    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    >
</head>

<body data-api="${pageContext.request.contextPath}/solicitacoes-coleta" data-id="id_solicitacao" data-base="${pageContext.request.contextPath}/Solicitações" data-columns="id_b2b,dt_solicitacao,litros_estimados,status">

    <!-- Menu lateral -->
    <aside class="menu">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

        <div class="caixa"></div>

        <nav>
            <a href="${pageContext.request.contextPath}/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp" class="ativo">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificados</a>
        </nav>

    </aside>

    <!-- Conteúdo principal -->
    <main class="conteudo">

        <header class="topo">
            <h1>Solicitações de coleta</h1>

            <a href="${pageContext.request.contextPath}/Solicitações/formulario.jsp" class="adicionar">
                + Adicionar Solicitação
            </a>
        </header>

        <!-- Tabela de solicitações -->
        <section class="tabela-container">

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

                <tbody>
                   <c:forEach var ="solicitacao" items="${solicitacoes}">
                    <tr>
                        <td>${solicitacao.id_b2b}</td>
                        <td>${solicitacao.dt_solicitacao}</td>
                        <td>${solicitacao.litros_estimados}</td>
                        <td>${solicitacao.status}</td>
                        <td class="acoes">
                            <button type="button" class="editar" title="Editar" onclick="window.location.href='${pageContext.request.contextPath}/Solicitações/formulario.jsp?id=${solicitacao.id_solicitacao}'">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>

                            <button type="button" class="excluir" title="Excluir" onclick="window.location.href='${pageContext.request.contextPath}/Solicitações/excluir.jsp?id=${solicitacao.id_solicitacao}'">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>

        </section>

    </main>

    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

</body>
</html>
