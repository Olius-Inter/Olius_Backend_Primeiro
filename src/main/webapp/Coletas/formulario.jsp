<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Coleta' : 'Nova Coleta'} - Olius</title>
    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adicionar/nova-coleta.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-api="${pageContext.request.contextPath}/coleta" data-id="id_coleta">

    <main class="formulario">

        <h1>${not empty param.id ? 'Editar Coleta' : 'Nova Coleta'}</h1>


        <!-- dados da coleta -->
        <form id="formColeta" action="${pageContext.request.contextPath}/coleta" method="post" data-retorno="${pageContext.request.contextPath}/Coletas/coletas.jsp" >


             <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>
            <div class="campos">

                <div class="campo">
                    <label for="data">Data da coleta *</label>
                    <input type="date" id="data" name="dt_coleta" required>
                </div>

                <div class="campo">
                    <label for="empresa">Solicitação (ID) *</label>
                    <input type="number" id="empresa" name="id_solicitacao" value="<c:out value='${coleta.id_solicitacao}'/>" required>
                </div>

                <div class="campo">
                    <label for="motorista">Motorista (ID) *</label>
                    <input type="number" id="motorista" name="id_motorista" value="<c:out value='${coleta.id_motorista}'/>" required>
                </div>

                <div class="campo">
                    <label for="volume">Volume coletado *</label>

                    <!-- volume em litros -->
                    <div class="volume-litros">
                        <input
                            type="number"
                            id="volume"
                            name="volume"
                            min="0.1"
                            step="0.1"
                            value="<c:out value='${coleta.volume}'/>"
                            required
                        >
                        <span>L</span>
                    </div>
                </div>

                <div class="campo campo-completo">
                    <label for="observacao">Observação</label>
                    <input type="text" id="observacao" name="observacao">
                </div>

            </div>

            <!--botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/Coletas/coletas.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/js/validar.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

</body>
</html>
