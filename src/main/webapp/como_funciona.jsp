<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Olius - Como Funciona</title>
    
    <!-- Arquivos de estilos CSS globais e específicos da página "Como Funciona" -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/como_funciona.css">
</head>
<body>

    <!-- ========================================================================= -->
    <!-- CABEÇALHO (HEADER): Logo, iluminação de fundo e navegação principal        -->
    <!-- ========================================================================= -->
    <header class="header">
        <div class="luz-amarela"></div>

        <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
            <img class="logo-icon" src="${pageContext.request.contextPath}/assets/global/logo-normal.svg" alt="Logotipo Olius">
            <span class="logo-texto">Olius</span>
        </a>

        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/como_funciona.jsp" class="active">Como funciona</a>
            <a href="${pageContext.request.contextPath}/planos.jsp">Planos</a>
            <a href="${pageContext.request.contextPath}/contato.jsp">Sobre</a>
            <a href="#agendar" class="btn-agendar">Agendar coleta</a>
            <a href="${pageContext.request.contextPath}/login?sair=1">Sair</a>
        </nav>
    </header>
    
    <main>
        <!-- ===================================================================== -->
        <!-- SEÇÃO HERO INICIAL: Título de introdução e mockups ilustrativos        -->
        <!-- ===================================================================== -->
        <section class="inicio">
            <div class="inicio-conteudo">
                <h1 class="o-olius-funciona-container">
                    <span>O Olius funciona de formas </span>
                    <span class="diferentes">diferentes,</span> mas com o mesmo propósito.
                </h1>
                <p class="empresas-e-cidadaos">
                    Empresas e cidadãos têm jornadas diferentes no app Olius, mas juntos transformamos óleo usado em impacto positivo.
                </p>
            </div>
            <div class="projeto-conteudo">
                <div class="projeto-mockups">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-web.png" alt="Mockup da plataforma Olius no computador" class="mockup-notebook">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-mob.png" alt="Mockup do aplicativo Olius no celular" class="mockup-mobile">
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO PARA EMPRESAS: Passo a passo e banner de recursos B2B           -->
        <!-- ===================================================================== -->
        <section class="secao-publico para-empresas">
            <header class="secao-header">
                <div class="icone-titulo">
                    <img src="${pageContext.request.contextPath}/assets/como-funciona/para-empresas.png" alt="Ícone de Empresas">
                    <h2>Para <span class="destaque">empresas</span></h2>
                </div>
                <p class="subtitulo">Gestão completa do descarte de óleo usado, com praticidade, segurança e transparência.</p>
            </header>

            <!-- Lista ordenada de etapas para empresas -->
            <ol class="passos-grid">
                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/cadastre.png" alt="Ícone Cadastro" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">01</span>
                            <h3>Cadastre sua empresa</h3>
                        </div>
                    </div>
                    <p>Informe os dados da sua empresa e do local de coleta</p>
                </li>
                
                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/agende.png" alt="Ícone Agendar" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">02</span>
                            <h3>Agende sua coleta</h3>
                        </div>
                    </div>
                    <p>Escolha a data e o horário que funcionam melhor para você</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/acompanhe.png" alt="Ícone Acompanhar" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">03</span>
                            <h3>Acompanhe em tempo real</h3>
                        </div>
                    </div>
                    <p>Veja o status da coleta e acompanhe cada etapa do processo</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/relatorios.png" alt="Ícone Relatórios" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">04</span>
                            <h3>Receba relatórios e certificados</h3>
                        </div>
                    </div>
                    <p>Tenha acesso a relatórios e certificados de destinação correta</p>
                </li>
            </ol>

            <!-- Banner resumido com os diferenciais B2B -->
            <div class="recursos-banner">
                <h3>Tudo em um só lugar</h3>
                <ul class="recursos-lista">
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/conformidade.png" alt="" aria-hidden="true"> Conformidade ambiental</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/relatorios-perso.png" alt="" aria-hidden="true"> Relatórios personalizados</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/certificados.png" alt="" aria-hidden="true"> Certificados de descarte</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/seguranca.png" alt="" aria-hidden="true"> Segurança e transparência</li>
                </ul>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO PARA CIDADÃOS: Passo a passo e benefícios B2C                   -->
        <!-- ===================================================================== -->
        <section class="secao-publico para-cidadaos">
            <header class="secao-header">
                <div class="icone-titulo">
                    <img src="${pageContext.request.contextPath}/assets/como-funciona/para-cidadaos.png" alt="Ícone de Cidadãos">
                    <h2>Para <span class="destaque">cidadãos</span></h2>
                </div>
                <p class="subtitulo">Prático, rápido e recompensador.<br>Descarte seu óleo e gere impacto.</p>
            </header>

            <!-- Lista ordenada de etapas para cidadãos -->
            <ol class="passos-grid">
                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/cadastre-se.png" alt="Ícone Cadastro" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">01</span>
                            <h3>Cadastre-se no app</h3>
                        </div>
                    </div>
                    <p>Crie sua conta de forma rápida e gratuita</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/encnotre-pontos.png" alt="Ícone Ponto" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">02</span>
                            <h3>Encontre um ponto de descarte</h3>
                        </div>
                    </div>
                    <p>Localize o ponto de coleta credenciado mais próximo de você</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/entregue.png" alt="Ícone Entrega" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">03</span>
                            <h3>Entregue seu óleo</h3>
                        </div>
                    </div>
                    <p>Realize a entrega no local selecionado e confirme pelo aplicativo</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/acompanhe-o-impacto.png" alt="Ícone Impacto" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">04</span>
                            <h3>Acompanhe seu impacto</h3>
                        </div>
                    </div>
                    <p>Ganhe pontos, suba de nível e veja o impacto positivo que você gera</p>
                </li>
            </ol>

            <!-- Banner resumido de engajamento B2C -->
            <div class="recursos-banner">
                <h3>Mais do que descartar, é transformar.</h3>
                <ul class="recursos-lista">
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/ganhe-pontos.png" alt="" aria-hidden="true"> Ganhe pontos e recompensas</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/acompanhe-o-impacto.png" alt="" aria-hidden="true"> Acompanhe seu impacto</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/descarte.png" alt="" aria-hidden="true"> Descarte fácil e consciente</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/seguranca.png" alt="" aria-hidden="true"> Seguro e confiável</li>
                </ul>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO PARA COLETORES: Fluxo de rotas e acompanhamento                 -->
        <!-- ===================================================================== -->
        <section class="secao-publico para-coletores">
            <header class="secao-header">
                <div class="icone-titulo">
                    <img src="${pageContext.request.contextPath}/assets/como-funciona/para-coletores.png" alt="Ícone de Coletores">
                    <h2>Para <span class="destaque">coletores</span></h2>
                </div>
                <p class="subtitulo">Organize suas rotas, coletas e resultados com eficiência e segurança.</p>
            </header>

            <!-- Lista ordenada de etapas para coletores -->
            <ol class="passos-grid">
                <li class="passo-card"> 
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/cadastre.png" alt="Ícone Cadastro" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">01</span>
                            <h3>Cadastre-se no app</h3>
                        </div>
                    </div>
                    <p>Acesse o app com suas credenciais</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/acompanhe.png" alt="Ícone Rotas" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">02</span>
                            <h3>Veja suas rotas</h3>
                        </div>
                    </div>
                    <p>Confira as coletas agendadas do dia e planeje seu trajeto</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/cadastre.png" alt="Ícone Coletas" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">03</span>
                            <h3>Realize as coletas</h3>
                        </div>
                    </div>
                    <p>Insira a coleta, informe a quantidade e tire foto se necessário</p>
                </li>

                <li class="passo-card">
                    <div class="passo-header">
                        <div class="passo-icone-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/como-funciona/desempenho.png" alt="Ícone Enviar" class="passo-icone">
                        </div>
                        <div class="passo-titulo-grupo">
                            <span class="passo-numero">04</span>
                            <h3>Envie e acompanhe</h3>
                        </div>
                    </div>
                    <p>Suas coletas são enviadas em tempo real e seu desempenho é registrado</p>
                </li>
            </ol>

            <!-- Banner resumido para coletores -->
            <div class="recursos-banner">
                <h3>Tudo para facilitar seu dia a dia</h3>
                <ul class="recursos-lista">
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/acompanhe.png" alt="" aria-hidden="true"> Rotas otimizadas</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/cadastre.png" alt="" aria-hidden="true"> Registro rápido e seguro</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/desempenho.png" alt="" aria-hidden="true"> Acompanhamento de desempenho</li>
                    <li><img src="${pageContext.request.contextPath}/assets/como-funciona/suporte.png" alt="" aria-hidden="true"> Suporte sempre que precisar</li>
                </ul>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO CTA (Call to Action): Chamada final para agendamento              -->
        <!-- ===================================================================== -->
        <section class="cta-banner">
            <div class="cta-conteudo">
                <h2>Faça parte da mudança.</h2>
                <p>Transforme o descarte de óleo em impacto positivo para o meio ambiente, sua comunidade e o futuro.</p>
            </div>
            
            <a href="#agendar" class="btn-cta">Agendar coleta agora</a>

            <!-- Imagem decorativa de respingo de óleo -->
            <img src="${pageContext.request.contextPath}/assets/global/oleo.png" alt="Respingo de óleo decorativo" class="cta-imagem-oleo">
        </section>
    </main>

    <!-- ========================================================================= -->
    <!-- RODAPÉ (FOOTER): Informações de contato, navegação e créditos               -->
    <!-- ========================================================================= -->
    <footer class="footer">
        <div class="footer-container">

            <!-- Logo e Slogan -->
            <div class="footer-brand">
                <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
                    <img class="logo-icon" src="${pageContext.request.contextPath}/assets/global/logo-branco.svg" alt="Logotipo Olius">
                    <span class="logo-texto">Olius</span>
                </a>
                <p class="tagline">Cada gota importa.</p>
            </div>
            
            <!-- Navegação -->
            <nav class="footer-links" aria-label="Links do Rodapé">
                <h4>Navegação</h4>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Início</a></li>
                    <li><a href="${pageContext.request.contextPath}/como_funciona.jsp">Como funciona</a></li>
                    <li><a href="${pageContext.request.contextPath}/planos.jsp">Planos</a></li>
                    <li><a href="${pageContext.request.contextPath}/contato.jsp">Sobre nós</a></li>
                </ul>
            </nav>

            <!-- Contato -->
            <div class="footer-contato">
                <h4>Contato</h4>
                <ul>
                    <li><a href="mailto:oliusjef26@gmail.com">oliusjef26@gmail.com</a></li>
                    <li><a href="https://www.instagram.com/olius.app" target="_blank" rel="noopener noreferrer">@olius.app</a></li>
                    <li><span>São Paulo, SP - Brasil</span></li>
                </ul>
            </div>

            <!-- Créditos e Acesso ADM -->
            <div class="footer-proposito">
                <h4>Desenvolvido com propósito para um futuro sustentável.</h4>
                <p class="copyright">© 2026 Olius. Todos os direitos reservados.</p>
                <a href="#" class="acesso-adm">Acesso ADM</a>
            </div>
        </div>
    </footer>

</body>
</html>