<%@ page pageEncoding="UTF-8" %>

    <!-- Cria a janela usada para cadastrar uma nova empresa ou editar uma empresa já cadastrada. -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Nova Empresa" data-editar="Editar Empresa">
        <header class="topo-modal">
            <h2 id="tituloFormulario">Nova Empresa</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>

        <!-- Parte superior da janela, onde aparece o título do formulário e o botão para fechá-lo. -->
        </header>

         <!-- Espaço onde aparecem mensagens para informar se alguma ação foi realizada ou se existe algum aviso. -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

        <!-- Formulário que recebe os dados da empresa e envia as informações para o servlet responsável pelo cadastro. Depois do envio, o usuário retorna para a página de empresas. -->
        <form id="formEmpresa" action="${pageContext.request.contextPath}/empresas" method="post" data-retorno="${pageContext.request.contextPath}/Empresas/empresa.jsp">

             <!-- Agrupa os campos com as informações principais da empresa, como nome, CNPJ, telefone e plano escolhido. -->
            <div class="campos">

                <div class="campo">
                    <label for="nome">Nome da empresa *</label>
                    <input type="text" id="nome" name="nome_empresa" maxlength="200" required>
                </div>

                <div class="campo">
                    <label for="cnpj">CNPJ *</label>
                    <input
                        type="text"
                        id="cnpj"
                        name="cnpj"
                        placeholder="00.000.000/0000-00"
                        maxlength="20"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="telefone">Telefone *</label>
                    <input
                        type="tel"
                        id="telefone"
                        name="telefone"
                        placeholder="(11) 99999-9999"
                        maxlength="30"
                        required
                    >
                </div>

                <div class="campo">
                    <label for="plano">Plano *</label>
                    <select id="plano" name="plano" required>
                        <option value="Basico">Básico</option>
                        <option value="Empresa">Empresa</option>
                        <option value="Profissional">Profissional</option>
                    </select>
                </div>
                <

                <!-- Parte do formulário destinada ao endereço da empresa, com campos para preencher o CEP, rua, número, complemento, bairro, cidade e estado. -->
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



            </div>

            <!-- Botões que permitem cancelar o preenchimento ou enviar as informações para cadastrar a empresa. -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                    Adicionar
                </button>

            </div>

        </form>
    </dialog>
