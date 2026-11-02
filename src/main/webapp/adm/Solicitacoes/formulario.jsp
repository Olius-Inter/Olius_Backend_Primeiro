<%@ page pageEncoding="UTF-8" %>

    <!-- Janela usada para cadastrar uma nova solicitação ou editar uma existente -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Nova Solicitação de Coleta" data-editar="Editar Solicitação de Coleta">
        <header class="topo-modal">
            <h2 id="tituloFormulario">Nova Solicitação de Coleta</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>
        </header>

        <!-- Espaço para exibir mensagens sobre o preenchimento do formulário -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

        <!-- Formulário que envia as informações da solicitação para o sistema -->
        <form id="formSolicitacao" action="${pageContext.request.contextPath}/solicitacoes-coleta" method="post" data-retorno="${pageContext.request.contextPath}/adm/Solicitacoes/solicitacoes.jsp">

            <div class="campos">

                <!-- Campo para informar a empresa responsável pela solicitação -->
                <div class="campo">
                    <label for="empresa">Empresa solicitante *</label>
                    <input
                        type="number"
                        id="empresa"
                        name="id_b2b"
                       
                        required
                    >
                </div>


               <!-- Campo para informar a quantidade de óleo estimada em litros -->
                <div class="campo campo-completo">
                    <label for="volume">Volume estimado *</label>

                     <!-- O valor informado representa a quantidade de litros -->
                    <div class="volume-litros">
                        <input
                            type="number"
                            id="volume"
                            name="litros_estimados"
                            min="0.1"
                            step="0.1"
                           
                            required
                        >
                        <span>L</span>
                    </div>
                </div>

                <!-- Campo para escolher a situação da solicitação -->
                <div class="campo campo-completo">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <option value="" disabled >Selecione o status</option>
                        <option value="Pendente">Pendente</option>
                        <option value="Realizada">Realizada</option>
                        <option value="Cancelada">Cancelada</option>
                    </select>
                </div>


            </div>

            <!-- Botões para cancelar ou salvar a solicitação -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     Adicionar
                </button>

            </div>

        </form>
    </dialog>
