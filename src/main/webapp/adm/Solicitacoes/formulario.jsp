<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${not empty param.id ? 'Editar Solicitação de Coleta' : 'Novo Solicitação de Coleta'} - Olius</title>

    <!-- estilos da página -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adm/css/adicionar/nova-solicitacao.css">

    <!-- fonte Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
</head>

<body data-api="${pageContext.request.contextPath}/solicitacoes-coleta" data-id="id_solicitacao">

    <main class="formulario">

        <!-- if para o título: se solicitacao.id estiver vazio, exibe 'Nova Solicitação de Coleta', senão exibe 'Editar Solicitação de Coleta' -->
        <h1>${not empty param.id ? 'Editar Solicitação de Coleta' : 'Novo Solicitação de Coleta'}</h1>


        <!-- dados da solicitação -->
        <form id="formSolicitacao" action="${pageContext.request.contextPath}/solicitacoes-coleta" method="post" data-retorno="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">

            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty param.id}">
                <input type="hidden" name="id" value="${param.id}">
            </c:if>

            <div class="campos">

                <div class="campo">
                    <label for="empresa">Empresa solicitante (ID) *</label>
                    <input
                        type="number"
                        id="empresa"
                        name="id_b2b"
                        value="<c:out value='${solicitacao.id_b2b}'/>"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="data">Data da solicitação *</label>
                    <input
                        type="date"
                        id="data"
                        name="dt_solicitacao"
                        value="<c:out value='${solicitacao.dt_solicitacao}'/>"
                        required
                    >
                </div>

                <div class="campo campo-completo">
                    <label for="volume">Volume estimado *</label>

                    <!-- volume em litros -->
                    <div class="volume-litros">
                        <input
                            type="number"
                            id="volume"
                            name="litros_estimados"
                            min="0.1"
                            step="0.1"
                            value="<c:out value='${solicitacao.litros_estimados}'/>"
                            required
                        >
                        <span>L</span>
                    </div>
                </div>

                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled ${empty solicitacao.status ? 'selected' : ''}>Selecione o status</option>
                        <option value="pendente" ${solicitacao.status == 'pendente' ? 'selected' : ''}>Pendente</option>
                        <option value="aprovada" ${solicitacao.status == 'aprovada' ? 'selected' : ''}>Aprovada</option>
                        <option value="em_andamento" ${solicitacao.status == 'em_andamento' ? 'selected' : ''}>Em andamento</option>
                        <option value="concluida" ${solicitacao.status == 'concluida' ? 'selected' : ''}>Concluída</option>
                        <option value="cancelada" ${solicitacao.status == 'cancelada' ? 'selected' : ''}>Cancelada</option>
                    </select>
                </div>


                <div class="campo">
                    <label for="id_pev">Ponto de coleta (ID) *</label>
                    <input
                        type="number"
                        id="id_pev"
                        name="id_pev"
                        required
                    >
                </div>
            </div>

            <!-- botões do formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" onclick="window.location.href='${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp'">Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     ${empty param.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

    <script src="${pageContext.request.contextPath}/adm/js/editar.js" defer></script>

</body>
</html>
