<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Coleta' : 'Novo Coleta'} - Olius</title>
    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/adicionar/nova-coleta.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body>

    <main class="formulario">

        <h1>${not empty param.id ? 'Nova Coleta' : 'Editar Coleta'}</h1>


        <!-- dados da coleta -->
        <form id="formColeta" action="${pageContext.request.contextPath}/coleta" method="post" >


             <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty coleta.id}">
                <input type="hidden" name="id" value="${coleta.id}">
            </c:if>
            <div class="campos">

                <div class="campo">
                    <label for="data">Data da coleta *</label>
                    <input type="date" id="data" name="data" required>
                </div>

                <div class="campo">
                    <label for="empresa">Empresa *</label>
                    <input type="text" id="empresa" name="empresa" value="<c:out value='${coleta.empresa}'/>" required>
                </div>

                <div class="campo">
                    <label for="motorista">Motorista *</label>
                    <input type="text" id="motorista" name="motorista" value="<c:out value='${coleta.motorista}'/>" required>
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
                    <label>Avaliação *</label>

                    <!-- nota da coleta -->
                    <div class="avaliacao" role="radiogroup" aria-label="Avaliação de 1 a 5 estrelas">

                        <input type="radio" id="estrela5" name="nota" value="5" required>
                        <label for="estrela5" title="5 estrelas" aria-label="5 estrelas">★</label>

                        <input type="radio" id="estrela4" name="nota" value="4">
                        <label for="estrela4" title="4 estrelas" aria-label="4 estrelas">★</label>

                        <input type="radio" id="estrela3" name="nota" value="3">
                        <label for="estrela3" title="3 estrelas" aria-label="3 estrelas">★</label>

                        <input type="radio" id="estrela2" name="nota" value="2">
                        <label for="estrela2" title="2 estrelas" aria-label="2 estrelas">★</label>

                        <input type="radio" id="estrela1" name="nota" value="1">
                        <label for="estrela1" title="1 estrela" aria-label="1 estrela">★</label>

                    </div>
                </div>

            </div>

            <!--botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/adm/Coletas/coletas.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty coleta.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

</body>
</html>
