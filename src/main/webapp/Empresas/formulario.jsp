<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Empresa' : 'Nova Empresa'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adicionar/nova-empresa.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-endereco="true" data-api="${pageContext.request.contextPath}/empresas" data-id="id_usuario">

    <!-- formulário de cadastro -->
    <main class="formulario">

        <!-- if para o título: se empresa.id estiver vazio, exibe 'Nova Empresa', senão exibe 'Editar Empresa' -->
        <h1>${not empty param.id ? 'Editar Empresa' : 'Nova Empresa'}</h1>


        <form id="formEmpresa" action="${pageContext.request.contextPath}/empresas" method="post" data-retorno="${pageContext.request.contextPath}/Empresas/empresa.jsp">


            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>

            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome da empresa *</label>
                    <input type="text" id="nome" name="nome_fantasia" value="<c:out value='${empresa.nome_fantasia}'/>" maxlength="200" required>
                </div>

                <div class="campo">
                    <label for="cnpj">CNPJ *</label>
                    <input
                        type="text"
                        id="cnpj"
                        name="cnpj"
                        placeholder="00.000.000/0000-00"
                        value="<c:out value='${empresa.cnpj}'/>" maxlength="20"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="email">Razão social *</label>
                    <input type="text" id="email" name="razao_social" value="<c:out value='${empresa.razao_social}'/>" maxlength="200" required>
                </div>

                <div class="campo">
                    <label for="telefone">Telefone *</label>
                    <input
                        type="tel"
                        id="telefone"
                        name="telefone"
                        placeholder="(11) 99999-9999"
                        value="<c:out value='${empresa.telefone}'/>" maxlength="30"
                        required
                    >
                </div>

                <!-- endereco proprio deste cadastro -->
                <div class="campo campo-completo">
                    <h2>Endereço</h2>
                </div>
                <div class="campo">
                    <label for="endereco_cep">CEP *</label>
                    <input type="text" id="endereco_cep" name="cep" maxlength="15" required>
                </div>
                <div class="campo">
                    <label for="endereco_logradouro">Logradouro *</label>
                    <input type="text" id="endereco_logradouro" name="logradouro" maxlength="200" required>
                </div>
                <div class="campo">
                    <label for="endereco_numero">Número *</label>
                    <input type="text" id="endereco_numero" name="numero" maxlength="20" required>
                </div>
                <div class="campo">
                    <label for="endereco_complemento">Complemento</label>
                    <input type="text" id="endereco_complemento" name="complemento" maxlength="100">
                </div>
                <div class="campo">
                    <label for="endereco_bairro">Bairro *</label>
                    <input type="text" id="endereco_bairro" name="bairro" maxlength="100" required>
                </div>
                <div class="campo">
                    <label for="endereco_cidade">Cidade *</label>
                    <input type="text" id="endereco_cidade" name="cidade" maxlength="100" required>
                </div>
                <div class="campo">
                    <label for="endereco_estado">Estado *</label>
                    <input type="text" id="endereco_estado" name="estado" maxlength="50" required>
                </div>
                <div class="campo">
                    <label for="endereco_pais">País *</label>
                    <input type="text" id="endereco_pais" name="pais" maxlength="50" required>
                </div>


            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/Empresas/empresa.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

</body>
</html>
