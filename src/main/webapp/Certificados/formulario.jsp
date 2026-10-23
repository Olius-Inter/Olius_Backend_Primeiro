<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

   <title>${not empty param.id ? 'Editar Certificado' : 'Novo Certificado'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adicionar/novo-certificado.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body>

    <main class="formulario">

        <h1>${not empty param.id  ? 'Novo Certificado' : 'Editar Certificado'}</h1>

        <!-- dados do certificado -->
        <form id="formCertificado" action="${pageContext.request.contextPath}/certificado" method="post" >


            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test="${not empty certificado.id}">
                <input type="hidden" name="id" value="${certificado.id}">
            </c:if>

            <div class="campos">

                <div class="campo">
                    <label for="empresa">Empresa *</label>
                    <input
                        type="text"
                        id="empresa"
                        name="empresa"
                        value="<c:out value='${certificado.empresa}'/>"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="dataEmissao">Data de emissão *</label>
                    <input
                        type="date"
                        id="dataEmissao"
                        name="dataEmissao"
                        value="<c:out value='${certificado.dataEmissao}'/>"
                        required
                    >
                </div>

                <div class="campo campo-volume">
                    <label for="volume">Volume total *</label>

                    <!--volume em litros -->
                    <div class="volume-litros">
                        <input
                            type="number"
                            id="volume"
                            name="volume"
                            min="0.1"
                            step="0.1"
                            value="<c:out value='${certificado.volume}'/>"
                            required
                        >
                        <span>L</span>
                    </div>
                </div>

                <div class="campo campo-completo">
                    <label for="arquivo">Arquivo PDF *</label>

                    <!-- seleção do arquivo -->
                    <div class="arquivo-container">

                        <span class="icone-arquivo" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="26" height="30" fill="none" stroke="currentColor" stroke-width="1.4">
                                <path d="M5 2.5h9l5 5V21H5z"/>
                                <path d="M14 2.5V8h5"/>
                                <path d="M8 13h8M8 16h8"/>
                            </svg>
                        </span>

                        <label for="arquivo" class="botao-arquivo">
                            Escolher arquivo
                        </label>

                        <input
                            type="file"
                            id="arquivo"
                            name="arquivo"
                            accept="application/pdf,.pdf"
                            value="<c:out value='${certificado.arquivo}'/>"
                            required
                        >

                        <span class="nome-arquivo"></span>

                    </div>
                </div>

            </div>

            <!--botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/Certificados/certificados.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     ${empty certificado.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>
    <c:if test="${not empty param.id}">
        <script src="${pageContext.request.contextPath}/js/editar.js" defer></script>
    </c:if>

</body>
</html>
