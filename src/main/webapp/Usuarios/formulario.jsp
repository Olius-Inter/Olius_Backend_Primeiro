<%@ page pageEncoding="UTF-8" %>

    <!-- Janela usada para cadastrar um novo usuário ou editar um cadastro existente -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Novo Usuário" data-editar="Editar Usuário">
        <header class="topo-modal">
            <h2 id="tituloFormulario">Novo Usuário</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>
        </header>

        <!-- Espaço para mostrar mensagens sobre o preenchimento do formulário -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

         <!-- Formulário que envia os dados do usuário para o sistema -->
        <form id="formUsuario" action="${pageContext.request.contextPath}/usuarios" method="post" data-retorno="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">

            <div class="campos">

                <!-- Campo para informar o nome do usuário -->
                <div class="campo">
                    <label for="nome">Nome *</label>
                    <input type="text" id="nome" name="nome" maxlength="255" required>
                </div>

                <!-- Campo para informar o e-mail do usuário -->
                <div class="campo">
                    <label for="email">E-mail *</label>
                    <input type="email" id="email" name="email" maxlength="255" required>
                </div>

                 <!-- Campo para informar o telefone de contato -->
                <div class="campo">
                    <label for="telefone">Telefone *</label>
                    <input type="tel" id="telefone" name="telefone" required>
                </div>

                <!-- Campo para informar o CPF do cliente B2C -->
                <div class="campo" id="dadosB2c">
                    <label for="cpf">CPF do cliente B2C *</label>
                    <input type="text" id="cpf" name="cpf" inputmode="numeric" maxlength="11" pattern="[0-9]{11}" placeholder="Somente os 11 números" required>
                </div>

                 <!-- Campo para registrar a data do primeiro cadastro -->
                <div class="campo campo-completo">
                    <label for="primeiro_registro">Primeiro registro *</label>
                    <input type="date" id="primeiro_registro" name="primeiro_registro" required>
                </div>

                <!-- Campo para cadastrar a senha do usuário -->
                <div class="campo campo-completo">
                    <label for="status">Senha *</label>
                    <input type="password" id="status" name="senha" minlength="6" maxlength="128" autocomplete="new-password" required>
                </div>
            </div>

            <!-- Botões para cancelar o cadastro ou enviar as informações -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     Adicionar
                </button>

            </div>

        </form>
    </dialog>
