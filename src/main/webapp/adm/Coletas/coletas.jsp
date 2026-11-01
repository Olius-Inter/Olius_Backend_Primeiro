<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Coletas realizadas - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/coletas.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<aside class="menu">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/adm/logo.png" alt="Logo Olius">
    </div>

    <div class="caixa"></div>

    <nav>
        <a href="${pageContext.request.contextPath}/adm.jsp">Dashboard</a>
        <a href="${pageContext.request.contextPath}/empresas.jsp">Empresas</a>
        <a href="${pageContext.request.contextPath}/usuarios.jsp">Usuários</a>
        <a href="${pageContext.request.contextPath}/pev.jsp">PEV</a>
        <a href="${pageContext.request.contextPath}/motoristas.jsp">Motoristas</a>
        <a href="${pageContext.request.contextPath}/solicitacoes.jsp">Solicitacoes de coleta</a>
        <a href="${pageContext.request.contextPath}/coletas.jsp" class="ativo">Coletas realizadas</a>
        <a href="${pageContext.request.contextPath}/certificados.jsp">Certificados</a>
    </nav>
</aside>

<main class="conteudo">

    <header class="topo">
        <h1>Coletas realizadas</h1>
        <button class="adicionar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Coletas/formulario.jsp>'">+ Adicionar Coleta</button>
    </header>

    <section class="tabela-container">
        <table>
            <thead>
            <tr>
                <th>Data da coleta</th>
                <th>Empresa</th>
                <th>Motorista</th>
                <th>Volume coletado</th>
                <th>Avaliação</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="coleta" items="${coletas}">
                <tr>
                    <td>${coleta.dataColeta}</td>
                    <td>${coleta.empresa}</td>
                    <td>${coleta.motorista}</td>
                    <td>${coleta.volumeColetado}</td>
                    <td>${coleta.avaliacao}</td>
                    <td class="acoes">
                        <button class="editar" title="Editar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Coletas/formulario.jsp?id=${coleta.id_coleta}'">
                            <i class="fa-regular fa-pen-to-square"></i>
                        </button>
                        <button class="excluir" title="Excluir" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Coletas/excluir.jsp?id=${coleta.id_coleta}'">
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