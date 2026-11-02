<%@ page pageEncoding="UTF-8" %>

    <!-- Janela usada para cadastrar um novo ponto de coleta ou editar um já existente -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Novo Ponto de Coleta" data-editar="Editar Ponto de Coleta">
        <header class="topo-modal">
            <h2 id="tituloFormulario">Novo Ponto de Coleta</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>
        </header>

         <!-- Espaço para mostrar mensagens sobre o preenchimento do formulário -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

        <!-- Formulário que envia os dados do ponto de coleta para o sistema -->
        <form id="formPEV" action="${pageContext.request.contextPath}/pev" method="post" data-retorno="${pageContext.request.contextPath}/adm/PEV/pev.jsp">



            <div class="campos">
                <!-- Campo para informar o usuário responsável pelo ponto de coleta -->
                <div class="campo">
                    <label for="id_usuario">Responsável pelo ponto (opcional)</label>
                    <input type="number" id="id_usuario" name="id_usuario">
                </div>

                 <!-- Campos para preencher o endereço completo do ponto de coleta -->
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
                    <input type="text" id="endereco_numero" name="numero" maxlength="10" required>
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
                    <input type="text" id="endereco_estado" name="estado" maxlength="2" required>
                </div>


                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled >Selecione o status</option>
                        <option value="Aprovada" >Aprovado</option>
                        <option value="Negado" >Negado</option>
                        <option value="Pendente" >Pendente</option>
                    </select>
                </div>

            </div>

             <!-- Botões para cancelar o cadastro ou enviar o formulário -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    Adicionar
                </button>

            </div>

        </form>
    </dialog>
