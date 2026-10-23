<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Usuários - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/usuarios.css">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body data-api="${pageContext.request.contextPath}/usuarios" data-id="id_usuario" data-base="${pageContext.request.contextPath}/Usuarios" data-columns="nome,email,telefone,tipo_usuario,primeiro_registro,id_usuario">

    <aside class="menu">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

         <div class="caixa"></div>

        <nav>
            <a href="${pageContext.request.contextPath}/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp" class="ativo">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificados</a>
        </nav>

    </aside>

    <main class="conteudo">

        <header class="topo">
            <h1>Usuários</h1>

            <button class="adicionar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Usuarios/formulario.jsp'">
                + Adicionar Usuário
            </button>
        </header>

        <section class="tabela-container">

            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>E-mail</th>
                        <th>Telefone</th>
                        <th>Tipo de usuário</th>
                        <th>Primeiro registro</th>
                        <th>Usuário (ID)</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="usuario" items ="${usuarios}">
                        <tr>
                            <td><c:out value="${usuario.nome}"/></td>
                            <td><c:out value="${usuario.email}"/></td>
                            <td><c:out value="${usuario.telefone}"/></td>
                            <td><c:out value="${usuario.tipoUsuario}"/></td>
                            <td><c:out value="${usuario.primeiroRegistro}"/></td>
                            <td><c:out value="${usuario.id_usuario}"/></td>
                            <td class="acoes">
                                <button class="editar" title="Editar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Usuarios/formulario.jsp?id=${usuario.id_usuario}'">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </button>

                                <button class="excluir" title="Excluir" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Usuarios/excluir.jsp?id=${usuario.id_usuario}'">
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