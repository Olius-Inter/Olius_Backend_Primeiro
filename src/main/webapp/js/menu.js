// Abre e fecha os mesmos links do menu no celular e no tablet.
(() => {
    const menu = document.querySelector('.menu');
    const botao = document.querySelector('.botao-menu');
    if (!menu || !botao) return;

    // Fecha o menu e atualiza o texto e o estado do botão.
    function fecharMenu() {
        menu.classList.remove('aberto');
        botao.setAttribute('aria-expanded', 'false');
        botao.textContent = 'Menu';
    }

    // Alterna entre abrir e fechar o menu quando o botão é pressionado.
    botao.addEventListener('click', () => {
        const aberto = menu.classList.toggle('aberto');
        botao.setAttribute('aria-expanded', String(aberto));
        botao.textContent = aberto ? 'Fechar menu' : 'Menu';
    });

    // Permite fechar o menu usando a tecla Escape e devolve o foco ao botão.
    menu.addEventListener('keydown', evento => {
        if (evento.key === 'Escape') {
            fecharMenu();
            botao.focus();
        }
    });

    // Fecha o menu automaticamente quando há uma mudança no tamanho da tela.
    window.matchMedia('(max-width: 900px)').addEventListener('change', fecharMenu);
})();
