<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Excluir Certificado - Olius</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

   <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/excluir/excluir.css">
</head>

<body>

    <!-- confirmação de exclusão -->
    <div class="confirmacao">

        <!-- titulo da janela -->
        <h1>Excluir Certificado</h1>

        <!-- pergunta para confirmar a exclusão -->
        <p>Você deseja <span>excluir</span>?</p>

        <!--botões para cancelar ou confirmar -->
        <div class="botoes">
            <button class="cancelar" type="button" data-destino="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp">
                Cancelar
            </button>

            <button class="excluir" type="button"
                    data-url="${pageContext.request.contextPath}/certificado" data-parametro="id_certificado"
                    data-id="<c:out value='${param.id}'/>">
                Excluir
            </button>
        </div>

    </div>
    <script src="${pageContext.request.contextPath}/adm/js/excluir.js" defer></script>

</body>
</html>