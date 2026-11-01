(() => {
    'use strict';

    // Localiza o formulário da página e define a quantidade de números esperada para cada documento.
    const formulario = document.querySelector('form[data-retorno]');
    if (!formulario) return;
    const documentos = { cpf: 11, cnpj: 14, cep: 8 };

    // aceita documentos colados com pontuacao, enviando apenas os numeros
    function normalizar(campo) {
        if (campo.name === 'cnpj') campo.value = campo.value.replace(/[.\-/\s]/g, '').toUpperCase();
        else if (documentos[campo.name]) campo.value = campo.value.replace(/\D/g, '');
        else if (campo.type !== 'password') campo.value = campo.value.trim();
    }

    // Verifica se cada campo foi preenchido corretamente, considerando seu tipo e suas regras.
    // Quando encontra algum problema, define uma mensagem para orientar o preenchimento.
    function validarCampo(campo) {
        campo.setCustomValidity('');
        if (campo.disabled || campo.readOnly || campo.type === 'hidden') return true;
        const valor = campo.value.trim();
        let mensagem = '';
        if (campo.required && !valor) mensagem = 'Preencha este campo.';
        else if (valor) {
            if (campo.name === 'cnpj' && !/^[A-Z0-9]{12}\d{2}$/.test(valor)) {
                mensagem = 'Informe um CNPJ com 14 caracteres.';
            } else if (campo.name !== 'cnpj' && documentos[campo.name] && !new RegExp('^\\d{' + documentos[campo.name] + '}$').test(valor)) {
                mensagem = 'Informe ' + documentos[campo.name] + ' números neste campo.';
            } else if (campo.name === 'telefone' && !/^\+?[\d\s().-]+$/.test(valor)) {
                mensagem = 'Informe um telefone válido.';
            } else if (campo.name === 'telefone' && !/^\d{10,15}$/.test(valor.replace(/\D/g, ''))) {
                mensagem = 'Informe o telefone com DDD, entre 10 e 15 números.';
            } else if (campo.name.startsWith('id_') && (!Number.isInteger(Number(valor)) || Number(valor) <= 0)) {
                mensagem = 'Selecione um cadastro válido.';
            } else if (campo.type === 'number') {
                const numero = Number(valor);
                if (!Number.isFinite(numero)) mensagem = 'Informe um número válido.';
                else if (['volume', 'volume_total', 'litros_estimados'].includes(campo.name) && numero <= 0) {
                    mensagem = 'Informe um volume maior que zero.';
                }
            } else if (campo.type === 'date' && !/^\d{4}-\d{2}-\d{2}$/.test(valor)) {
                mensagem = 'Informe uma data válida.';
            }
            if (!mensagem && campo.maxLength > 0 && campo.value.length > campo.maxLength) {
                mensagem = 'Este campo aceita até ' + campo.maxLength + ' caracteres.';
            }
        }
        campo.setCustomValidity(mensagem);
        campo.setAttribute('aria-invalid', String(!campo.validity.valid));
        return campo.validity.valid;
    }

    // chamada pelo fluxo de cadastro/edicao antes de enviar a requisicao
    window.validarFormulario = function (form) {
        let valido = true;
        form.querySelectorAll('input, select, textarea').forEach(campo => {
            if (!campo.disabled && !campo.readOnly) normalizar(campo);
            if (!validarCampo(campo)) valido = false;
        });
        if (!valido || !form.checkValidity()) {
            form.reportValidity();
            return false;
        }
        return true;
    };

    // Verifica o campo enquanto a pessoa digita, permitindo identificar erros antes do envio.
    formulario.addEventListener('input', evento => {
        const campo = evento.target;
        if (campo.matches('input, select, textarea')) validarCampo(campo);
    });

    // Quando o valor de um campo é alterado, padroniza a informação e verifica se está correta.
    formulario.addEventListener('change', evento => {
        const campo = evento.target;
        if (campo.matches('input, select, textarea')) {
            normalizar(campo);
            validarCampo(campo);
        }
    });

    // Ao colar CPF, CNPJ ou CEP, remove os caracteres de pontuação e mantém apenas o formato esperado.
    formulario.addEventListener('paste', evento => {
        const campo = evento.target;
        if (!documentos[campo.name] || campo.disabled || campo.readOnly) return;
        const texto = evento.clipboardData.getData('text');
        if (!(campo.name === 'cnpj' ? /^[A-Za-z0-9\s.\-/]+$/ : /^[\d\s.\-/]+$/).test(texto)) return;
        evento.preventDefault();
        campo.value = campo.name === 'cnpj' ? texto.replace(/[.\-/\s]/g, '').toUpperCase() : texto.replace(/\D/g, '');
        validarCampo(campo);
    });
})();
