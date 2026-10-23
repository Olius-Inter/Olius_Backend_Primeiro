<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Ponto de Coleta' : 'Novo Ponto de Coleta'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adicionar/novo-pev.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-endereco="true" data-api="${pageContext.request.contextPath}/pev" data-id="id_pev">

    <main class="formulario">

        <h1>${not empty param.id ? 'Editar Ponto de Coleta' : 'Novo Ponto de Coleta'}</h1>


        <!-- dados do ponto de coleta -->
        <form id="formPEV" action="${pageContext.request.contextPath}/pev" method="post" data-retorno="${pageContext.request.contextPath}/PEV/pev.jsp">

            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>



            <div class="campos">

                <div class="campo">
                    <label for="nome">QR Code *</label>
                    <input type="text" id="nome" name="qr_code" value="<c:out value='${PEV.qr_code}'/>" maxlength="255" required>
                </div>

                <div class="campo">
                    <label for="responsavel">Empresa responsável (ID) *</label>
                    <input type="number" id="responsavel" name="id_usuario_b2b" value="<c:out value='${PEV.id_usuario_b2b}'/>" required>
                </div>

                <div class="campo">
                    <label for="id_usuario_b2c">Usuário B2C responsável (ID) *</label>
                    <input type="number" id="id_usuario_b2c" name="id_usuario_b2c" value="<c:out value='${PEV.id_usuario_b2c}'/>" required>
                </div>

                <div class="campo">
                    <label for="dt_aprovacao">Data de aprovação *</label>
                    <input type="date" id="dt_aprovacao" name="dt_aprovacao" required>
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


                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled ${empty PEV.status ? 'selected' : ''}>Selecione o status</option>
                        <option value="ativo" ${PEV.status == 'ativo' ? 'selected' : ''}>Ativo</option>
                        <option value="inativo" ${PEV.status == 'inativo' ? 'selected' : ''}>Inativo</option>
                        <option value="pendente" ${PEV.status == 'pendente' ? 'selected' : ''}>Pendente</option>
                    </select>
                </div>

            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/PEV/pev.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>

</body>
</html>
