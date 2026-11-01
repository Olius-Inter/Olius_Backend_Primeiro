(() => {
    'use strict';

    // Busca os elementos do dashboard que serão atualizados com os dados do sistema.
    const base = document.body.dataset.base;
    const aviso = document.getElementById('mensagemDashboard');
    const atualizar = document.getElementById('atualizarDashboard');
    const filtro = document.getElementById('periodoColetas');
    const rosca = document.querySelector('.grafico-rosca');
    const legenda = document.querySelector('.legenda');
    const grafico = document.querySelector('.linha-svg');
    let coletas = [];
    let carregando = false;
    const cores = ['#ffd13f', '#70ce8a', '#f39b43', '#729bd5', '#da7777', '#aaa39a'];

    // Padroniza os textos para que os status sejam reconhecidos mesmo com diferenças de acentos ou letras maiúsculas.
    function normalizar(texto) {
        return String(texto || '').trim().toLowerCase().normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '').replace(/[ _-]+/g, ' ');
    }

    // Separa as solicitações de coleta de acordo com o status e atualiza o gráfico de rosca e sua legenda.
    function mostrarSolicitacoes(dados) {
        const grupos = { Pendentes: 0, Concluídas: 0, 'Em andamento': 0, Aprovadas: 0, Canceladas: 0, Outros: 0 };
        dados.forEach(item => {
            const status = normalizar(item.status);
            if (['pendente', 'pendentes'].includes(status)) grupos.Pendentes++;
            else if (['concluida', 'concluido', 'realizada', 'realizado'].includes(status)) grupos.Concluídas++;
            else if (status === 'em andamento') grupos['Em andamento']++;
            else if (['aprovada', 'aprovado'].includes(status)) grupos.Aprovadas++;
            else if (['cancelada', 'cancelado'].includes(status)) grupos.Canceladas++;
            else grupos.Outros++;
        });
        legenda.replaceChildren();
        let inicio = 0;
        const partes = [];
        Object.entries(grupos).forEach(([nome, quantidade], indice) => {
            if (!quantidade) return;
            const fim = inicio + quantidade / dados.length * 360;
            partes.push(`${cores[indice]} ${inicio}deg ${fim}deg`);
            inicio = fim;
            const linha = document.createElement('li');
            const ponto = document.createElement('span');
            ponto.className = 'ponto';
            ponto.style.backgroundColor = cores[indice];
            const texto = document.createElement('span');
            texto.textContent = `${nome}: ${quantidade}`;
            linha.append(ponto, texto);
            legenda.append(linha);
        });
        if (!dados.length) {
            const linha = document.createElement('li');
            linha.textContent = 'Nenhuma solicitação cadastrada.';
            legenda.append(linha);
        }
        rosca.style.background = partes.length ? `conic-gradient(${partes.join(',')})` : '#eeeae3';
        rosca.setAttribute('aria-label', dados.length ? Array.from(legenda.children).map(linha => linha.textContent).join('; ') : 'Sem solicitações');
        document.querySelector('[data-indicador="solicitacoes-coleta"]').textContent = grupos.Pendentes;
    }

    // Organiza as coletas por mês, considerando o período escolhido no filtro, e atualiza o gráfico de linhas.
    function mostrarColetas() {
        const quantidade = Number(filtro.value);
        const hoje = new Date();
        const meses = [];
        for (let i = quantidade - 1; i >= 0; i--) {
            const data = new Date(hoje.getFullYear(), hoje.getMonth() - i, 1);
            meses.push({ chave: data.getFullYear() + '-' + String(data.getMonth() + 1).padStart(2, '0'),
                nome: data.toLocaleDateString('pt-BR', { month: 'short', year: '2-digit' }),
                rotulo: data.toLocaleDateString('pt-BR', { month: 'short' }), total: 0 });
        }
        coletas.forEach(item => {
            const data = String(item.dt_solicitacao || '');
            if (!/^\d{4}-\d{2}-\d{2}$/.test(data)) return;
            const mes = meses.find(m => m.chave === data.substring(0, 7));
            if (mes) mes.total++;
        });
        const maximo = Math.max(5, ...meses.map(m => m.total));
        const escala = Math.ceil(maximo / 5) * 5;
        const pontos = meses.map((mes, i) => `${i * 600 / (quantidade - 1)},${160 - mes.total * 150 / escala}`);
        grafico.querySelector('.linha-dados').setAttribute('points', pontos.join(' '));
        grafico.querySelector('.area-preenchida').setAttribute('points', pontos.join(' ') + ' 600,160 0,160');
        const marcadores = grafico.querySelector('.marcadores');
        marcadores.replaceChildren();
        const nomes = document.querySelector('.meses');
        nomes.replaceChildren();
        meses.forEach((mes, i) => {
            const circulo = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
            circulo.setAttribute('cx', i * 600 / (quantidade - 1));
            circulo.setAttribute('cy', 160 - mes.total * 150 / escala);
            circulo.setAttribute('r', '4');
            const titulo = document.createElementNS('http://www.w3.org/2000/svg', 'title');
            titulo.textContent = mes.nome + ': ' + mes.total + ' coleta(s)';
            circulo.append(titulo);
            marcadores.append(circulo);
            const nome = document.createElement('span');
            nome.textContent = mes.rotulo;
            nome.title = mes.nome;
            nomes.append(nome);
        });
        const eixo = document.querySelector('.eixo-y');
        eixo.replaceChildren();
        for (let i = 5; i >= 0; i--) {
            const valor = document.createElement('span');
            valor.textContent = escala / 5 * i;
            eixo.append(valor);
        }
        grafico.setAttribute('aria-label', meses.map(m => m.nome + ': ' + m.total + ' coletas').join('; '));
        document.getElementById('resumoColetas').textContent = meses.reduce((total, m) => total + m.total, 0) + ' coleta(s) no período.';
    }

    // Consulta os dados das diferentes áreas do sistema e atualiza os indicadores do dashboard.
    // Também controla o carregamento e apresenta mensagens caso alguma consulta não funcione.
    async function carregar() {
        if (carregando) return;
        carregando = true;
        atualizar.disabled = true;
        filtro.disabled = true;
        aviso.textContent = 'Carregando indicadores…';
        const falhas = [];
        const rotas = ['empresas', 'usuarios', 'pev', 'motoristas', 'solicitacoes-coleta', 'certificado'];
        document.querySelectorAll('[data-indicador]').forEach(campo => { campo.textContent = '…'; });
        await Promise.all(rotas.map(async rota => {
            try {
                const resposta = await fetch(base + '/' + rota, { headers: { Accept: 'application/json' }, cache: 'no-store' });
                if (!resposta.ok) throw new Error('Falha na consulta');
                const dados = await resposta.json();
                if (!Array.isArray(dados)) throw new Error('Resposta inválida');
                document.querySelector(`[data-indicador="${rota}"]`).textContent = dados.length;
                if (rota === 'solicitacoes-coleta') {
                    mostrarSolicitacoes(dados);
                    coletas = dados.filter(item => item.status === 'Realizada');
                    document.querySelector('[data-indicador="coleta"]').textContent = coletas.length;
                    filtro.disabled = false;
                    mostrarColetas();
                }
            } catch (_) {
                const campo = document.querySelector(`[data-indicador="${rota}"]`);
                campo.textContent = '—';
                falhas.push(campo.nextElementSibling.textContent);
                if (rota === 'solicitacoes-coleta') {
                    rosca.style.background = '#eeeae3';
                    rosca.setAttribute('aria-label', 'Solicitações indisponíveis');
                    legenda.replaceChildren();
                    const linha = document.createElement('li');
                    linha.textContent = 'Não foi possível consultar as solicitações.';
                    legenda.append(linha);
                    document.querySelector('[data-indicador="coleta"]').textContent = '—';
                    grafico.querySelector('.linha-dados').setAttribute('points', '');
                    grafico.querySelector('.area-preenchida').setAttribute('points', '');
                    grafico.querySelector('.marcadores').replaceChildren();
                    grafico.setAttribute('aria-label', 'Coletas indisponíveis');
                    document.getElementById('resumoColetas').textContent = 'Não foi possível consultar as coletas.';
                }
            }
        }));
        aviso.textContent = falhas.length ? 'Não foi possível atualizar: ' + falhas.join(', ') + '. Use Atualizar para tentar novamente.' : 'Indicadores atualizados.';
        aviso.setAttribute('role', falhas.length ? 'alert' : 'status');
        atualizar.disabled = false;
        carregando = false;
    }

    // Atualiza o gráfico quando o período é alterado e recarrega os dados pelo botão.
    filtro.addEventListener('change', mostrarColetas);
    atualizar.addEventListener('click', carregar);
    carregar();
})();
