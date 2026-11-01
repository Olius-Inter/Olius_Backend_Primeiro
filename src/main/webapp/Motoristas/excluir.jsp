<%@ page pageEncoding="UTF-8" %>
    <!-- Cria a janela que aparece quando o usuário escolhe excluir um motorista. Ela permite conferir a operação antes de confirmar. -->
    <dialog id="modalExcluir" class="modal modal-excluir" aria-labelledby="tituloExcluir">

         <!-- Mostra o título da janela e o espaço onde aparece a informação do motorista que será excluído. -->
        <h2 id="tituloExcluir">Excluir registro</h2>
        <p id="confirmacaoExcluir"></p>

         <!-- Mostra mensagens para informar o usuário sobre o resultado da exclusão ou possíveis avisos. -->
        <p id="mensagemExcluir" class="aviso" role="status"></p>

         <!-- Apresenta os botões para cancelar a operação ou confirmar a exclusão do motorista selecionado. -->
        <div class="botoes-formulario">
            <button type="button" class="botao-cancelar" data-fechar-modal>Cancelar</button>
            <button type="button" class="botao-excluir" id="confirmarExclusao">Excluir</button>
        </div>
    </dialog>
