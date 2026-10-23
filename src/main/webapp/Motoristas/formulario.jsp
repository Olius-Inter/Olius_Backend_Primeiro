<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Motorista' : 'Novo Motorista'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adicionar/novo-motorista.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-api="${pageContext.request.contextPath}/motoristas" data-id="id_motorista">

    <main class="formulario">

        <h1>${not empty param.id ? 'Editar Motorista' : 'Novo Motorista'}</h1>

        <!-- dados do motorista -->
        <form id="formMotorista" action="${pageContext.request.contextPath}/motoristas" method="post" data-retorno="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">


            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>


            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome do motorista *</label>
                    <input
                        type="text"
                        id="nome"
                        name="nome"
                        value="<c:out value='${motorista.nome}'/>" maxlength="150"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="cpf">CPF (opcional)</label>
                    <input
                        type="text"
                        id="cpf"
                        name="cpf"
                        value="<c:out value='${motorista.cpf}'/>" maxlength="15"
                    >
                </div>

                <div class="campo">
                    <label for="empresa">Empresa responsável *</label>
                    <input
                        type="text"
                        id="empresa"
                        name="empresa"
                        value="<c:out value='${motorista.empresa}'/>" maxlength="150"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="cnh">Licença (CNH) *</label>
                    <input
                        type="text"
                        id="cnh"
                        name="cnh"
                        value="<c:out value='${motorista.cnh}'/>" maxlength="20"
                        required
                    >
                </div>

                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled ${empty motorista.status ? 'selected' : ''}>Selecione o status</option>
                        <option value="ativo" ${motorista.status == 'ativo' ? 'selected' : ''}>Ativo</option>
                        <option value="inativo" ${motorista.status == 'inativo' ? 'selected' : ''}>Inativo</option>
                        <option value="pendente" ${motorista.status == 'pendente' ? 'selected' : ''}>Pendente</option>
                    </select>
                </div>


                <div class="campo">
                    <label for="telefone">Telefone *</label>
                    <input
                        type="tel"
                        id="telefone"
                        name="telefone"
                        required
                     maxlength="30">
                </div>
            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/Motoristas/motoristas.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

</body>
</html>
