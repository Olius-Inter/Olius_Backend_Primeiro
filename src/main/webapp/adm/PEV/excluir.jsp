<%@ page pageEncoding="UTF-8" %>
    <!-- Janela que aparece para confirmar a exclusão de um registro -->
    <dialog id="modalExcluir" class="modal modal-excluir" aria-labelledby="tituloExcluir">
        <h2 id="tituloExcluir">Excluir registro</h2>

        <!-- Mensagens exibidas durante a confirmação da exclusão -->
        <p id="confirmacaoExcluir"></p>
        <p id="mensagemExcluir" class="aviso" role="status"></p>

         <!-- Botões para cancelar ou confirmar a exclusão -->
        <div class="botoes-formulario">
            <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>
            <button type="button" class="botao-excluir" id="confirmarExclusao">Excluir</button>
        </div>
    </dialog>
