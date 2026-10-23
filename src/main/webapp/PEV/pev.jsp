<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Pontos de Coleta - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pev.css">

    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap"
        rel="stylesheet"
    >

    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    >
</head>

<body>

    <!-- Menu lateral -->
    <aside class="menu">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

         <div class="caixa"></div>
        <nav>
            <a href="${pageContext.request.contextPath}/adm.html">Dashboard</a>
            <a href="${pageContext.request.contextPath}/empresas.html">Empresas</a>
            <a href="${pageContext.request.contextPath}/usuarios.html">Usuários</a>
            <a href="${pageContext.request.contextPath}/pev.html" class="ativo">PEV</a>
            <a href="${pageContext.request.contextPath}/motoristas.html">Motoristas</a>
            <a href="${pageContext.request.contextPath}/solicitacoes.html">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/coletas.html">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/certificados.html">Certificados</a>
        </nav>

    </aside>

    <!-- Conteúdo principal -->
    <main class="conteudo">

        <header class="topo">
            <h1>Pontos de coleta (PEV)</h1>

            <a href="novo-pev.html" class="adicionar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/PEV/formulario.jsp>'">
                + Adicionar PEV
            </a>
        </header>

        <!-- Tabela de pontos de coleta -->
        <section class="tabela-container">

            <table>
                <thead>
                    <tr>
                        <th>Responsável</th>
                        <th>Data de aprovação</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="pev" items = ${pevs}>
                        <tr>
                            <td><c:out value="${pev.responsavel}"/></td>
                            <td><c:out value="${pev.dataAprovacao}"/></td>
                            <td><c:out value="${pev.status}"/></td>
                            <td class="acoes">
                            <button type="button" class="editar" title="Editar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/PEV/formulario.jsp?id=${pev.id_pev}'">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>

                            <button type="button" class="excluir" title="Excluir" type="button" onclick="window.location.href='${pageContext.request.contextPath}/PEV/excluir.jsp?id=${pev.id_pev}'">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </section>

    </main>

</body>
</html>
