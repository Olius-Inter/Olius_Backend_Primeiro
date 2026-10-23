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

<body>

    <aside class="menu">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

         <div class="caixa"></div>

        <nav>
            <a href="${pageContext.request.contextPath}/adm.html">Dashboard</a>
            <a href="${pageContext.request.contextPath}/empresas.html">Empresas</a>
            <a href="${pageContext.request.contextPath}/usuarios.html" class="ativo">Usuários</a>
            <a href="${pageContext.request.contextPath}/pev.html">PEV</a>
            <a href="${pageContext.request.contextPath}/motoristas.html">Motoristas</a>
            <a href="${pageContext.request.contextPath}/solicitacoes.html">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/coletas.html">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/certificados.html">Certificados</a>
        </nav>

    </aside>

    <main class="conteudo">

        <header class="topo">
            <h1>Usuários</h1>

            <button class="adicionar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Certificados/formulario.jsp>'">
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
                        <th>Endereço</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>

                    <c: forEach var="usuário" items ="${usuarios}">
                        <tr>
                            <td><c:out value="${usuário.nome}"/></td>
                            <td><c:out value="${usuário.email}"/></td>
                            <td><c:out value="${usuário.telefone}"/></td>
                            <td><c:out value="${usuário.tipoUsuario}"/></td>
                            <td><c:out value="${usuário.endereco}"/></td>
                            <td><c:out value="${usuário.status}"/></td>
                            <td class="acoes">
                                <button class="editar" title="Editar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Usuarios/formulario.jsp?id=${usuario.id_usuario}'">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </button>

                                <button class="excluir" title="Excluir" type="button" onclick="window.location.href='${pageContext.request.contextPath}/Usuarios/excluir.jsp?id=${usuario.id_usuario}'">
                                    <i class="fa-regular fa-trash-can"></i>
                                </button>
                            </td>
                        </tr>

                    </c: forEach>

                </tbody>
            </table>

        </section>

    </main>

</body>
</html>