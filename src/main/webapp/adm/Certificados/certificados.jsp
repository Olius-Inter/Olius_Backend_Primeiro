<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Certificados - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/certificados.css">
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
            <a href="${pageContext.request.contextPath}/adm/index.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adm/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/adm/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/adm/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp" class="ativo">Certificados</a>
        </nav>
    </aside>

    <main class="conteudo">

        <header class="topo">
            <h1>Certificados</h1>
            <button class="adicionar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Certificados/formulario.jsp>'">+ Adicionar Certificado</button>
        </header>

        <section class="tabela-container">
            <table>
                <thead>
                    <tr>
                        <th>Empresa</th>
                        <th>Data de emissão</th>
                        <th>Volume total</th>
                        <th>Arquivo PDF</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var = "certificado" items = "${certificados}">
                        <tr>
                            <td>${certificado.empresa}</td>
                            <td>${certificado.dataEmissao}</td>
                            <td>${certificado.volumeTotal}</td>
                            <td>${certificado.arquivoPDF}</td>
                            <td class="acoes">
                                <button class="editar" title="Editar" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Certificados/formulario.jsp?id=${certificado.id_certificado}'">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </button>
                                <button class="excluir" title="Excluir" type="button" onclick="window.location.href='${pageContext.request.contextPath}/adm/Certificados/excluir.jsp?id=${certificado.id_certificado}'">
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