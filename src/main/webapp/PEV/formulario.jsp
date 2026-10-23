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

<body>

    <main class="formulario">

        <h1>${not empty PEV.id ? 'Novo Ponto de Coleta' : 'Editar Ponto de Coleta'}</h1>


        <!-- dados do ponto de coleta -->
        <form id="formPEV" action="${pageContext.request.contextPath}/PEV" method="post">

            <!-- verifica se o id existe, se existir: mostra o id-->
            <c:if test = "${not empty PEV.id}">
                <input type="hidden" name="id" value="${PEV.id}">
            </c:if>



            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome do ponto de coleta *</label>
                    <input type="text" id="nome" name="nome" value="<c:out value='${PEV.nome}'/>"required>
                </div>

                <div class="campo">
                    <label for="responsavel">Responsável *</label>
                    <input type="text" id="responsavel" name="responsavel" value="<c:out value='${PEV.responsavel}'/>" required>
                </div>

                <div class="campo">
                    <label for="cidade">Cidade *</label>
                    <input type="text" id="cidade" name="cidade" value="<c:out value='${PEV.cidade}'/>" required>
                </div>

                <div class="campo">
                    <label for="estado">Estado *</label>
                    <select id="estado" name="estado" required>
                        <option value="" selected disabled ${empty PEV.estado ? 'selected' : ''}>Selecione o estado</option>
                        <option value="AC" ${PEV.estado == 'AC' ? 'selected' : ''}>Acre</option>
                        <option value="AL" ${PEV.estado == 'AL' ? 'selected' : ''}>Alagoas</option>
                        <option value="AP" ${PEV.estado == 'AP' ? 'selected' : ''}>Amapá</option>
                        <option value="AM" ${PEV.estado == 'AM' ? 'selected' : ''}>Amazonas</option>
                        <option value="BA" ${PEV.estado == 'BA' ? 'selected' : ''}>Bahia</option>
                        <option value="CE" ${PEV.estado == 'CE' ? 'selected' : ''}>Ceará</option>
                        <option value="DF" ${PEV.estado == 'DF' ? 'selected' : ''}>Distrito Federal</option>
                        <option value="ES" ${PEV.estado == 'ES' ? 'selected' : ''}>Espírito Santo</option>
                        <option value="GO" ${PEV.estado == 'GO' ? 'selected' : ''}>Goiás</option>
                        <option value="MA" ${PEV.estado == 'MA' ? 'selected' : ''}>Maranhão</option>
                        <option value="MT" ${PEV.estado == 'MT' ? 'selected' : ''}>Mato Grosso</option>
                        <option value="MS" ${PEV.estado == 'MS' ? 'selected' : ''}>Mato Grosso do Sul</option>
                        <option value="MG" ${PEV.estado == 'MG' ? 'selected' : ''}>Minas Gerais</option>
                        <option value="PA" ${PEV.estado == 'PA' ? 'selected' : ''}>Pará</option>
                        <option value="PB" ${PEV.estado == 'PB' ? 'selected' : ''}>Paraíba</option>
                        <option value="PR" ${PEV.estado == 'PR' ? 'selected' : ''}>Paraná</option>
                        <option value="PE" ${PEV.estado == 'PE' ? 'selected' : ''}>Pernambuco</option>
                        <option value="PI" ${PEV.estado == 'PI' ? 'selected' : ''}>Piauí</option>
                        <option value="RJ" ${PEV.estado == 'RJ' ? 'selected' : ''}>Rio de Janeiro</option>
                        <option value="RN" ${PEV.estado == 'RN' ? 'selected' : ''}>Rio Grande do Norte</option>
                        <option value="RS" ${PEV.estado == 'RS' ? 'selected' : ''}>Rio Grande do Sul</option>
                        <option value="RO" ${PEV.estado == 'RO' ? 'selected' : ''}>Rondônia</option>
                        <option value="RR" ${PEV.estado == 'RR' ? 'selected' : ''}>Roraima</option>
                        <option value="SC" ${PEV.estado == 'SC' ? 'selected' : ''}>Santa Catarina</option>
                        <option value="SP" ${PEV.estado == 'SP' ? 'selected' : ''}>São Paulo</option>
                        <option value="SE" ${PEV.estado == 'SE' ? 'selected' : ''}>Sergipe</option>
                        <option value="TO" ${PEV.estado == 'TO' ? 'selected' : ''}>Tocantins</option>
                    </select>
                </div>

                <div class="campo campo-completo">
                    <label for="endereco">Endereço *</label>
                    <input type="text" id="endereco" name="endereco" value="<c:out value='${PEV.endereco}'/>" required>
                </div>

                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" selected disabled ${empty PEV.status ? 'selected' : ''}>Selecione o status</option>
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
                    ${empty PEV.id ? 'Adicionar' : 'Salvar'}
                </button>

            </div>

        </form>

    </main>

</body>
</html>
