<%@ page pageEncoding="UTF-8" %>

    <!-- Cria a janela usada para cadastrar um novo motorista ou editar um motorista já cadastrado. -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Novo Motorista" data-editar="Editar Motorista">

         <!-- Parte superior da janela, onde aparece o título do formulário e o botão para fechá-lo. -->
        <header class="topo-modal">
            <h2 id="tituloFormulario">Novo Motorista</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>
        </header>

        <!-- Espaço onde aparecem mensagens para informar se alguma ação foi realizada ou se existe algum aviso. -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

        <!-- Formulário que recebe os dados do motorista e envia as informações para o servlet responsável pelo cadastro. Depois do envio, o usuário retorna para a página de motoristas. -->
        <form id="formMotorista" action="${pageContext.request.contextPath}/motoristas" method="post" data-retorno="${pageContext.request.contextPath}/adm/Motoristas/motoristas.jsp">


             <!-- Agrupa os campos com as informações do motorista, incluindo nome, empresa responsável, CNH, status e telefone. -->
            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome do motorista *</label>
                    <input
                        type="text"
                        id="nome"
                        name="nome"
                        maxlength="150"
                        required
                    >
                </div>


                <div class="campo">
                    <label for="empresa">Empresa responsável *</label>
                    <input
                        type="text"
                        id="empresa"
                        name="empresa"
                        maxlength="150"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="cnh">Licença (CNH) *</label>
                    <input
                        type="text"
                        id="cnh"
                        name="cnh"
                        maxlength="50"
                        required
                    >
                </div>

                 <!-- Campo que permite escolher a situação atual do motorista, indicando se ele está disponível ou ocupado. -->
                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled >Selecione o status</option>
                        <option value="Disponivel">Disponível</option>
                        <option value="Ocupado">Ocupado</option>
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

            <!-- Botões que permitem cancelar o preenchimento ou enviar as informações para cadastrar o motorista. -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    Adicionar
                </button>

            </div>

        </form>
    </dialog>
