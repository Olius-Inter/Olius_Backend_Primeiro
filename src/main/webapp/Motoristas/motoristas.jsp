<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Motoristas - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/motoristas.css">

    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap"
        rel="stylesheet"
    >

    <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    >
</head>

<body data-api="${pageContext.request.contextPath}/motoristas" data-id="id_motorista" data-base="${pageContext.request.contextPath}/Motoristas" data-columns="nome,telefone,empresa,cnh,status">

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
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp" class="ativo">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificados</a>
        </nav>

    </aside>

    <!-- Conteúdo principal -->
    <main class="conteudo">

        <header class="topo">
            <h1>Motoristas</h1>

            <a href="${pageContext.request.contextPath}/Motoristas/formulario.jsp" class="adicionar">
                + Adicionar Motorista
            </a>
        </header>

        <!-- Tabela de motoristas -->
        <section class="tabela-container">

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

                <tbody>

                    <c:forEach var="motorista" items = "${motoristas}">
                        <tr>
                            <td>${motorista.nome}</td>
                            <td>${motorista.telefone}</td>
                            <td>${motorista.empresa}</td>
                            <td>${motorista.cnh}</td>
                            <td>${motorista.status}</td>
                            <td class="acoes">
                                <button type="button" class="editar" title="Editar" onclick="window.location.href='${pageContext.request.contextPath}/Motoristas/formulario.jsp?id=${motorista.id_motorista}'">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </button>

                                <button type="button" class="excluir" title="Excluir" onclick="window.location.href='${pageContext.request.contextPath}/Motoristas/excluir.jsp?id=${motorista.id_motorista}'">
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
