<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Usuário' : 'Novo Usuário'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/adicionar/novo-usuario.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-api="${pageContext.request.contextPath}/usuarios" data-id="id_usuario">

    <main class="formulario">

        <h1>${not empty param.id ? 'Editar Usuário' : 'Novo Usuário'}</h1>


        <!-- dados do usuário -->
        <form id="formUsuario" action="${pageContext.request.contextPath}/usuarios" method="post" data-retorno="${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp">

            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>

            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome *</label>
                    <input type="text" id="nome" name="nome" maxlength="255" value="<c:out value='${usuario.nome}'/>" required>
                </div>

                <div class="campo">
                    <label for="email">E-mail *</label>
                    <input type="email" id="email" name="email" value="<c:out value='${usuario.email}'/>" maxlength="255" required>
                </div>

                <div class="campo">
                    <label for="telefone">Telefone *</label>
                    <input type="tel" id="telefone" name="telefone" value="<c:out value='${usuario.telefone}'/>" required>
                </div>

                <div class="campo">
                    <label for="tipoUsuario">Tipo de usuário *</label>
                    <select id="tipoUsuario" name="tipo_usuario" required>
                        <option value="" disabled ${empty usuario.tipoUsuario ? 'selected' : ''}>Selecione o tipo</option>
                        <option value="B2B" ${usuario.tipoUsuario == 'B2B' ? 'selected' : ''}>B2B - Empresa</option>
                        <option value="B2C" ${usuario.tipoUsuario == 'B2C' ? 'selected' : ''}>B2C - Usuário comum</option>
                    </select>
                </div>

                <div class="campo campo-completo">
                    <label for="endereco">Primeiro registro *</label>
                    <input type="date" id="endereco" name="primeiro_registro" required>
                </div>

                <div class="campo campo-completo">
                    <label for="status">Senha *</label>
                    <input type="password" id="status" name="senha" required maxlength="18">
                </div>
            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/adm/Usuarios/usuarios.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

</body>
</html>
