<%@ page pageEncoding="UTF-8" %>

    <!-- Cria a janela usada para adicionar uma nova certificação ou editar uma já cadastrada -->
    <dialog id="modalFormulario" class="modal" aria-labelledby="tituloFormulario" data-novo="Nova Certificação" data-editar="Editar Certificação">

        <!-- Parte superior da janela, onde aparece o título do formulario e o botão para fechá-lo -->
        <header class="topo-modal">
            <h2 id="tituloFormulario">Nova Certificação</h2>
            <button type="button" data-fechar-modal aria-label="Fechar formulário">×</button>
        </header>

        <!-- Espaço onde aparecem mensagens para informar se alguma ação foi realizada ou se existe algum aviso. -->
        <p id="mensagemFormulario" class="aviso" role="status"></p>

        <! Formulario que recebe as informações da certificação e envia os dados para o servlet responsável por esse cadastro. Depois do envio, o usuario retorna para a página de certificações -->
        <form id="formCertificado" action="${pageContext.request.contextPath}/certificado" method="post" data-retorno="${pageContext.request.contextPath}/adm/Certificados/certificados.jsp" >

            <!-- Agrupa os campos que precisam ser preenchidos para cadastrar uma certificação, como a empresa responsável, o volume de óleo e o arquivo PDF -->
            <div class="campos">

                <div class="campo">
                    <label for="empresa">Empresa *</label>
                    <input
                        type="number"
                        id="empresa"
                        name="id_usuario_b2b"
                       
                        required
                    >
                </div>

                <!-- Campo usado para informar a quantidade de oleo coletado, com a medida em litros.  -->
                <div class="campo campo-volume">
                    <label for="volume">Volume total *</label>

                    <!--volume em litros -->
                    <div class="volume-litros">
                        <input
                            type="number"
                            id="volume"
                            name="volume_total"
                            min="0.1"
                            step="0.1"
                           
                            required
                        >
                        <span>L</span>
                    </div>
                </div>

                <!-- Area usada para informar o caminho ou o endereço do arquivo PDF da certficação, com um ícone para facilitar sua identificação -->
                <div class="campo campo-completo">
                    <label for="arquivo">Caminho ou URL do PDF *</label>

                    <!-- caminho do arquivo PDF -->
                    <div class="arquivo-container">

                        <span class="icone-arquivo" aria-hidden="true">
                            <svg viewBox="0 0 24 24" width="26" height="30" fill="none" stroke="currentColor" stroke-width="1.4">
                                <path d="M5 2.5h9l5 5V21H5z"/>
                                <path d="M14 2.5V8h5"/>
                                <path d="M8 13h8M8 16h8"/>
                            </svg>
                        </span>

                        <label for="arquivo" class="botao-arquivo">
                            Informar arquivo
                        </label>

                        <input
                            type="text"
                            id="arquivo"
                            name="arquivo_pdf"
                            maxlength="255"
                            required
                        >

                        <span class="nome-arquivo"></span>

                    </div>
                </div>



            </div>

            <!--botões do formulário que permitem cancelar o preenchimento ou enviar as informações para cadastrar a certificação -->
            <div class="botoes-formulario">

                <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>

                <button type="submit" class="botao-adicionar">
                     Adicionar
                </button>

            </div>

        </form>
    </dialog>
