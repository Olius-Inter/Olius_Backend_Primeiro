(() => {
    'use strict';
    const pagina = document.body;
    const api = pagina.dataset.api;
    if (!api) return;

    // Identifica a página atual e guarda as informações necessárias para consultar os dados.
    const idCampo = pagina.dataset.id;
    let enderecos = [];
    let registros = [];
    let usuarios = [];
    let empresas = [];
    let motoristas = [];
    let solicitacoes = [];
    const busca = document.getElementById('busca');
    const filtro = document.getElementById('filtroCampo');
    const campoFiltro = pagina.dataset.filtroStatus === 'false' ? null : pagina.dataset.columns.split(',').find(campo => campo === 'status' || campo === 'tipo_usuario');
    const aviso = document.createElement('p');
    aviso.id = 'mensagem';
    aviso.className = 'aviso';
    aviso.setAttribute('role', 'status');
    const formulario = document.querySelector('form');
    const tabela = document.querySelector('tbody');
    let id = null;
    let idExcluir = null;
    let ocupado = false;
    let abertura = 0;
    const modal = document.getElementById('modalFormulario');
    const modalExcluir = document.getElementById('modalExcluir');
    const mensagemFormulario = document.getElementById('mensagemFormulario');
    const mensagemExcluir = document.getElementById('mensagemExcluir');
    document.querySelector('.tabela-container').before(aviso);

    // Exibe mensagens de carregamento, sucesso ou erro na página.
    function informar(texto, erro = false) {
        aviso.textContent = texto;
        aviso.classList.toggle('erro', erro);
        aviso.setAttribute('role', erro ? 'alert' : 'status');
    }

    // Faz as requisições ao servidor e verifica se a resposta recebida está correta.
    // Caso ocorra algum problema, apresenta uma mensagem explicando o erro.
    async function requisitar(url, opcoes = {}) {
        const resposta = await fetch(url, { ...opcoes, headers: { Accept: 'application/json', ...opcoes.headers } });
        let dados;
        try {
            dados = await resposta.json();
        } catch (_) {
            throw new Error('O servidor não retornou uma resposta válida. Verifique se a aplicação está disponível.');
        }
        if (!resposta.ok) throw new Error(dados.erro || 'Não foi possível concluir a operação.');
        return dados;
    }

    // Busca os registros da página e confere se o servidor retornou uma lista válida.
    async function listar() {
        const dados = await requisitar(api);
        if (!Array.isArray(dados)) throw new Error('A listagem recebida é inválida.');
        return dados;
    }

    // Converte as datas do formato usado pelo sistema para o formato brasileiro.
    function formatarData(valor) {
        const data = String(valor || '').match(/^(\d{4})-(\d{2})-(\d{2})$/);
        return data ? `${data[3]}/${data[2]}/${data[1]}` : String(valor || '');
    }

    // Localiza o nome da empresa usando o ID que está salvo no registro.
    // Dessa forma, a tabela mostra o nome em vez de apresentar apenas um número.
    function nomeEmpresa(idEmpresa) {
        const empresa = empresas.find(item => String(item.id_usuario) === String(idEmpresa));
        return empresa ? empresa.nome_empresa || empresa.razao_social || 'Empresa sem nome' : 'Empresa não encontrada';
    }

    // Junta as principais informações de uma solicitação para facilitar sua identificação.
    function descricaoSolicitacao(solicitacao) {
        return [nomeEmpresa(solicitacao.id_b2b), formatarData(solicitacao.dt_solicitacao),
            `${solicitacao.litros_estimados} L`].join(' — ');
    }

    // Substitui IDs de relacionamentos pelos nomes correspondentes, facilitando a leitura dos registros.
    function valorExibido(campo, valor) {
        if (valor == null || valor === '') return valor;
        if (campo === 'id_usuario') {
            const usuario = usuarios.find(item => String(item.id_usuario) === String(valor));
            return usuario ? usuario.nome : 'Usuário não encontrado';
        }
        if (campo === 'id_b2b' || campo === 'id_usuario_b2b') return nomeEmpresa(valor);
        if (campo === 'id_motorista') {
            const motorista = motoristas.find(item => String(item.id_motorista) === String(valor));
            return motorista ? motorista.nome : 'Motorista não encontrado';
        }
        if (campo === 'id_solicitacao') {
            const solicitacao = solicitacoes.find(item => String(item.id_solicitacao) === String(valor));
            return solicitacao ? descricaoSolicitacao(solicitacao) : 'Solicitação não encontrada';
        }
        return valor;
    }

    // Formata os valores antes de exibi-los na tabela, como endereços, datas e volumes.
    // Quando o registro possui um PDF, cria um link para abrir o arquivo.
    function mostrarValor(celula, campo, valor) {
        valor = valorExibido(campo, valor);
        if (valor == null || valor === '') {
            celula.textContent = '—';
            return;
        }
        if (campo === 'id_endereco') {
            const endereco = enderecos.find(item => String(item.id_endereco) === String(valor));
            celula.textContent = endereco ? [endereco.logradouro, endereco.numero, endereco.cidade, endereco.estado].filter(Boolean).join(', ') : String(valor);
        } else if (campo.startsWith('dt_') || campo === 'primeiro_registro') {
            const data = String(valor).match(/^(\d{4})-(\d{2})-(\d{2})$/);
            celula.textContent = data ? `${data[3]}/${data[2]}/${data[1]}` : String(valor);
        } else if (campo.includes('volume') || campo === 'litros_estimados' || campo === 'qtd_litros') {
            celula.textContent = `${Number(valor).toLocaleString('pt-BR')} L`;
        } else if (campo === 'arquivo_pdf') {
            let endereco;
            try {
                endereco = new URL(String(valor), new URL(api.substring(0, api.lastIndexOf('/') + 1), location.href));
            } catch (_) {
                celula.textContent = String(valor);
                return;
            }
            if (['http:', 'https:'].includes(endereco.protocol)) {
                const link = document.createElement('a');
                link.href = endereco.href;
                link.textContent = 'Abrir PDF';
                link.target = '_blank';
                link.rel = 'noopener noreferrer';
                celula.append(link);
            } else celula.textContent = String(valor);
        } else celula.textContent = String(valor);
    }

    // Busca os registros e também os dados relacionados que serão usados na tabela.
    // Depois, prepara as opções dos filtros e chama a função que monta a listagem.
    async function carregarTabela() {
        informar('Carregando registros…');
        tabela.replaceChildren();
        const dados = await listar();
        const colunas = pagina.dataset.columns.split(',');
        if (colunas.includes('id_endereco')) {
            enderecos = await requisitar(api.substring(0, api.lastIndexOf('/') + 1) + 'enderecos');
        }
        const caminho = api.substring(0, api.lastIndexOf('/') + 1);
        const consultas = [];
        if (colunas.includes('id_usuario')) {
            consultas.push(requisitar(caminho + 'usuarios?referencias=true').then(dados => { usuarios = dados; }));
        }
        if (colunas.some(campo => ['id_b2b', 'id_usuario_b2b', 'id_solicitacao'].includes(campo))) {
            consultas.push(requisitar(caminho + 'empresas').then(dados => { empresas = dados; }));
        }
        if (colunas.includes('id_motorista')) {
            consultas.push(requisitar(caminho + 'motoristas').then(dados => { motoristas = dados; }));
        }
        if (colunas.includes('id_solicitacao')) {
            consultas.push(requisitar(caminho + 'solicitacoes-coleta').then(dados => { solicitacoes = dados; }));
        }
        await Promise.all(consultas);
        registros = pagina.dataset.status ? dados.filter(item => item.status === pagina.dataset.status) : dados;
        if (campoFiltro) {
            const escolhido = filtro.value;
            filtro.replaceChildren(new Option('Todos', ''));
            [...new Set(dados.map(registro => registro[campoFiltro]).filter(Boolean))].sort().forEach(valor => {
                filtro.append(new Option(valor, valor));
            });
            filtro.value = escolhido;
            document.getElementById('filtroCampoContainer').hidden = false;
            document.getElementById('rotuloFiltro').textContent = campoFiltro === 'status' ? 'Status' : 'Tipo de usuário';
        }
        mostrarTabela();
    }

    // Filtra os registros ja consultados, sem gravar ou mudar o banco.
    function normalizarTexto(valor) {
        return String(valor == null ? '' : valor).normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
    }


    // Aplica a busca e o filtro selecionado e monta novamente as linhas da tabela.
    // Também cria os botões que permitem editar ou excluir cada registro.
    function mostrarTabela() {
        tabela.replaceChildren();
        const colunas = pagina.dataset.columns.split(',');
        const texto = normalizarTexto(busca.value.trim());
        const dados = registros.filter(registro => {
            const endereco = enderecos.find(item => String(item.id_endereco) === String(registro.id_endereco));
            const valores = colunas.map(campo => valorExibido(campo, registro[campo]));
            if (endereco) valores.push(endereco.logradouro, endereco.numero, endereco.cidade, endereco.estado);
            return (!campoFiltro || !filtro.value || registro[campoFiltro] === filtro.value) && normalizarTexto(valores.join(' ')).includes(texto);
        });
        if (!dados.length) {
            const linha = tabela.insertRow();
            const celula = linha.insertCell();
            celula.colSpan = colunas.length + (pagina.dataset.readonly ? 0 : 1);
            celula.className = 'vazio';
            celula.textContent = registros.length ? 'Nenhum registro encontrado com esses filtros.' : (pagina.dataset.readonly ? 'Nenhuma solicitação realizada.' : 'Nenhum registro cadastrado. Use o botão Adicionar para começar.');
        }
        dados.forEach(registro => {
            const linha = tabela.insertRow();
            const titulos = document.querySelectorAll('thead th');
            colunas.forEach((campo, indice) => {
                const celula = linha.insertCell();
                celula.dataset.label = titulos[indice].textContent.trim();
                mostrarValor(celula, campo, registro[campo]);
            });
            if (pagina.dataset.readonly) return;
            const acoes = linha.insertCell();
            acoes.className = 'acoes';
            [['formulario', 'editar', 'Editar'], ['excluir', 'excluir', 'Excluir']].forEach(([arquivo, classe, texto]) => {
                const botao = document.createElement('button');
                botao.type = 'button';
                botao.className = classe;
                botao.title = texto;
                botao.setAttribute('aria-label', `${texto} registro ${registro[idCampo]}`);
                const icone = document.createElement('i');
                icone.className = classe === 'editar' ? 'fa-regular fa-pen-to-square' : 'fa-regular fa-trash-can';
                icone.setAttribute('aria-hidden', 'true');
                const rotulo = document.createElement('span');
                rotulo.textContent = texto;
                botao.append(icone, rotulo);
                botao.addEventListener('click', () => {
                    if (classe === 'editar') abrirFormulario(String(registro[idCampo]));
                    else abrirExclusao(String(registro[idCampo]));
                });
                acoes.append(botao);
            });
        });
        informar(`${dados.length} de ${registros.length} registro(s) encontrado(s).`);
    }

    // Carrega as informações de outros cadastros para preencher os campos de seleção do formulário.
    // Assim, a pessoa pode escolher um nome ou uma descrição em vez de precisar digitar um ID.
    async function carregarReferencias(tentativa) {
        const caminho = api.substring(0, api.lastIndexOf('/') + 1);
        const referencias = {
            id_endereco: ['enderecos', 'id_endereco', ['logradouro', 'numero', 'cidade']],
            id_usuario: ['usuarios', 'id_usuario', ['nome', 'email']],
            id_b2b: ['empresas', 'id_usuario', ['nome_empresa', 'cnpj']],
            id_usuario_b2b: ['empresas', 'id_usuario', ['nome_empresa', 'cnpj']],
            id_b2c: ['b2c-usuarios', 'id_usuario', ['cpf']],
            id_usuario_b2c: ['b2c-usuarios', 'id_usuario', ['cpf']],
            id_pev: ['pev', 'id_pev', ['qr_code', 'status']],
            id_motorista: ['motoristas', 'id_motorista', ['nome', 'cnh']],
            id_solicitacao: ['solicitacoes-coleta', 'id_solicitacao', ['dt_solicitacao', 'status']],
            id_entrega: ['entregas-pev', 'id_entrega', ['dt_entrega', 'qtd_litros']],
            id_carteira: ['carteira', 'id_carteira', ['patente', 'nivel']],
            id_participacao: ['participacoes-evento', 'id_participacao', ['status']],
            id_evento: ['eventos', 'id_evento', ['nome']]
        };
        for (const campo of formulario.querySelectorAll('[name^="id_"]')) {
            const referencia = referencias[campo.name];
            if (!referencia) continue;
            const [rota, chave, rotulos] = referencia;
            const dados = await requisitar(caminho + rota);
            if (tentativa !== abertura || !modal.open) return;
            if (!Array.isArray(dados)) throw new Error('Não foi possível carregar os cadastros relacionados.');
            const selecao = document.createElement('select');
            selecao.name = campo.name;
            selecao.id = campo.id;
            selecao.required = campo.required;
            selecao.disabled = true;
            selecao.append(new Option(campo.required ? 'Selecione um cadastro' : 'Sem vínculo', ''));
            if (campo.name === 'id_solicitacao') {
                empresas = await requisitar(caminho + 'empresas');
                if (tentativa !== abertura || !modal.open) return;
            }
            dados.forEach(item => {
                let rotulo = rotulos.map(nome => item[nome]).filter(valor => valor != null && valor !== '')
                    .map(formatarData).join(' - ');
                if (campo.name === 'id_solicitacao') rotulo = descricaoSolicitacao(item);
                selecao.append(new Option(rotulo, item[chave]));
            });
            campo.replaceWith(selecao);
        }
    }

    // Exibe mensagens dentro dos formulários e identifica quando se trata de um erro.
    function mensagem(elemento, texto, erro = false) {
        elemento.textContent = texto;
        elemento.classList.toggle('erro', erro);
        elemento.setAttribute('role', erro ? 'alert' : 'status');
    }

    // Bloqueia ou libera os campos enquanto o sistema está carregando ou salvando informações.
    function bloquearCampos(bloquear) {
        formulario.querySelectorAll('input, select, textarea').forEach(campo => {
            campo.disabled = bloquear;
        });
        formulario.querySelector('[type="submit"]').disabled = bloquear;
    }

    // Abre o formulário para um novo cadastro ou para editar um registro existente.
    // Quando é uma edição, busca os dados atuais e preenche os campos automaticamente.
    async function abrirFormulario(identificador = null) {
        if (ocupado || modal.open) return;
        const tentativa = ++abertura;
        id = identificador;
        formulario.reset();
        formulario.querySelectorAll('input, select, textarea').forEach(campo => {
            campo.readOnly = false;
            campo.setCustomValidity('');
            campo.removeAttribute('aria-invalid');
        });
        const senha = formulario.elements.namedItem('senha');
        if (senha) senha.required = !id;
        document.getElementById('tituloFormulario').textContent = id ? modal.dataset.editar : modal.dataset.novo;
        formulario.querySelector('[type="submit"]').textContent = id ? 'Salvar' : 'Adicionar';
        mensagem(mensagemFormulario, 'Carregando dados…');
        modal.showModal();
        document.body.classList.add('modal-aberto');
        bloquearCampos(true);
        try {
            await carregarReferencias(tentativa);
            if (tentativa !== abertura || !modal.open) return;
            if (id) {
                const registro = (await listar()).find(item => String(item[idCampo]) === id);
                if (tentativa !== abertura || !modal.open) return;
                if (!registro) throw new Error('Registro não encontrado. Atualize a listagem.');
                Object.entries(registro).forEach(([nome, valor]) => {
                    const campo = formulario.elements.namedItem(nome);
                    if (campo && nome !== 'senha') {
                        if (campo.tagName === 'SELECT' && valor != null && valor !== '' &&
                                !Array.from(campo.options).some(opcao => opcao.value === String(valor))) {
                            campo.append(new Option(String(valor), String(valor)));
                        }
                        campo.value = valor == null ? '' : valor;
                    }
                });
                if (pagina.dataset.endereco && registro.id_endereco != null) {
                    const dados = await requisitar(api.substring(0, api.lastIndexOf('/') + 1) + 'enderecos');
                    if (tentativa !== abertura || !modal.open) return;
                    const endereco = dados.find(item => String(item.id_endereco) === String(registro.id_endereco));
                    if (!endereco) throw new Error('Endereço do cadastro não encontrado.');
                    Object.entries(endereco).forEach(([nome, valor]) => {
                        const campo = formulario.elements.namedItem(nome);
                        if (campo) campo.value = valor == null ? '' : valor;
                    });
                }
                const primeiroRegistro = formulario.elements.namedItem('primeiro_registro');
                if (primeiroRegistro) primeiroRegistro.readOnly = true;
            }
            bloquearCampos(false);
            mensagem(mensagemFormulario, id && senha ? 'Deixe a senha em branco para manter a atual.' : '');
            const primeiro = formulario.querySelector('input:not(:disabled):not([readonly]), select:not(:disabled)');
            if (primeiro) primeiro.focus();
        } catch (erro) {
            if (tentativa === abertura && modal.open) mensagem(mensagemFormulario, erro.message, true);
        }
    }

    // Guarda o registro escolhido e abre a janela de confirmação antes de realizar a exclusão.
    function abrirExclusao(identificador) {
        if (ocupado) return;
        idExcluir = identificador;
        const registro = registros.find(item => String(item[idCampo]) === identificador);
        const descricao = registro ? pagina.dataset.columns.split(',').slice(0, 2)
            .map(campo => valorExibido(campo, registro[campo])).filter(Boolean).map(formatarData).join(' — ') : 'selecionado';
        document.getElementById('confirmacaoExcluir').textContent = 'Deseja excluir o registro: ' + descricao + '?';
        mensagem(mensagemExcluir, '');
        modalExcluir.showModal();
        document.body.classList.add('modal-aberto');
    }

    busca.addEventListener('input', mostrarTabela);
    filtro.addEventListener('change', mostrarTabela);
    document.getElementById('limparFiltros').addEventListener('click', () => {
        busca.value = '';
        filtro.value = '';
        mostrarTabela();
        busca.focus();
    });
    if (formulario) {
        document.querySelector('[data-adicionar]').addEventListener('click', () => abrirFormulario());
        document.querySelectorAll('dialog').forEach(janela => {
            janela.querySelectorAll('[data-fechar-modal]').forEach(botao => {
                botao.addEventListener('click', () => { if (!ocupado) janela.close(); });
            });
            janela.addEventListener('cancel', evento => { if (ocupado) evento.preventDefault(); });
            janela.addEventListener('close', () => {
                abertura++;
                document.body.classList.remove('modal-aberto');
            });
        });

        // Confere os campos e envia os dados ao servidor para cadastrar ou atualizar um registro.
        // Depois que a operação termina, fecha o formulário e carrega novamente a tabela
        formulario.addEventListener('submit', async evento => {
            evento.preventDefault();
            if (ocupado || formulario.querySelector('[type="submit"]').disabled) return;
            if (window.validarFormulario && !window.validarFormulario(formulario)) return;
            if (!formulario.reportValidity()) return;
            const parametros = new URLSearchParams(new FormData(formulario));
            const destino = new URL(formulario.action);
            const opcoes = { method: id ? 'PUT' : 'POST' };
            if (id) {
                parametros.set(idCampo, id);
                // Os servlets existentes leem os parametros do PUT pela URL.
                if (formulario.elements.namedItem('senha')) {
                    opcoes.method = 'POST';
                    parametros.set('_metodo', 'PUT');
                    opcoes.body = parametros;
                } else destino.search = parametros.toString();
            } else opcoes.body = parametros;
            ocupado = true;
            bloquearCampos(true);
            mensagem(mensagemFormulario, 'Salvando…');
            try {
                await requisitar(destino, opcoes);
            } catch (erro) {
                ocupado = false;
                bloquearCampos(false);
                mensagem(mensagemFormulario, erro.message, true);
                return;
            }
            ocupado = false;
            modal.close();
            carregarTabela().catch(erro => informar('Registro salvo. ' + erro.message, true));
        });

        // Envia ao servidor o pedido de exclusão do registro confirmado e atualiza a listagem.
        document.getElementById('confirmarExclusao').addEventListener('click', async evento => {
            if (ocupado || !idExcluir) return;
            const botao = evento.currentTarget;
            const destino = new URL(api, location.href);
            destino.searchParams.set(idCampo, idExcluir);
            ocupado = true;
            botao.disabled = true;
            mensagem(mensagemExcluir, 'Excluindo…');
            try {
                await requisitar(destino, { method: 'DELETE' });
            } catch (erro) {
                ocupado = false;
                botao.disabled = false;
                mensagem(mensagemExcluir, erro.message, true);
                return;
            }
            ocupado = false;
            botao.disabled = false;
            modalExcluir.close();
            carregarTabela().catch(erro => informar('Registro excluído. ' + erro.message, true));
        });

    }

    // Faz o carregamento inicial da tabela. Se houver erro, mostra a mensagem e permite tentar novamente.
    if (tabela) carregarTabela().catch(erro => {
        informar(erro.message, true);
        const tentar = document.createElement('button');
        tentar.type = 'button';
        tentar.textContent = 'Tentar novamente';
        tentar.addEventListener('click', () => location.reload());
        aviso.append(' ', tentar);
    });
})();
