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

<body>

    <main class="formulario">

        <h1>${not empty param.id ? 'Novo Motorista' : 'Editar Motorista'}</h1>

        <!-- dados do motorista -->
        <form id="formMotorista" action="${pageContext.request.contextPath}/motorista" method="post">


            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty motorista.id}">
                <input type="hidden" name="id" value="${motorista.id}">
            </c:if>


            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome do motorista *</label>
                    <input
                        type="text"
                        id="nome"
                        name="nome"
                        value="<c:out value='${motorista.nome}'/>"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="email">E-mail *</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value="<c:out value='${motorista.email}'/>"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="empresa">Empresa responsável *</label>
                    <input
                        type="text"
                        id="empresa"
                        name="empresa"
                        value="<c:out value='${motorista.empresa}'/>"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="licenca">Licença (CNH) *</label>
                    <input
                        type="text"
                        id="licenca"
                        name="licenca"
                        value="<c:out value='${motorista.licenca}'/>"
                        required
                    >
                </div>

                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" selected disabled ${empty motorista.status ? 'selected' : ''}>Selecione o status</option>
                        <option value="ativo" ${motorista.status == 'ativo' ? 'selected' : ''}>Ativo</option>
                        <option value="inativo" ${motorista.status == 'inativo' ? 'selected' : ''}>Inativo</option>
                        <option value="pendente" ${motorista.status == 'pendente' ? 'selected' : ''}>Pendente</option>
                    </select>
                </div>

            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/Motoristas/motoristas.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty motorista.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

</body>
</html>
