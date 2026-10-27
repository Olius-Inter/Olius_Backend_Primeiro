<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Olius - Planos e Assinaturas</title>

    <!-- Arquivos de estilos CSS globais e específicos da página de Planos -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/planos.css">
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
            <a href="${pageContext.request.contextPath}/como_funciona.jsp">Como funciona</a>
            <a href="${pageContext.request.contextPath}/planos.jsp" class="active">Planos</a>
            <a href="${pageContext.request.contextPath}/contato.jsp">Sobre</a>
            <a href="#agendar" class="btn-agendar">Agendar coleta</a>
            <a href="${pageContext.request.contextPath}/login?sair=1">Sair</a>
        </nav>
    </header>

    <main>
        <!-- ===================================================================== -->
        <!-- SEÇÃO HERO INICIAL: Título e introdução dos planos                     -->
        <!-- ===================================================================== -->
        <section class="planos-hero">
            <div class="hero-texto">
                <h1>Planos que se adaptam ao seu <span class="highlight">compromisso</span> com o planeta.</h1>
                <p>Escolha o plano ideal para as suas necessidades e acompanhe de perto o impacto que você gera.</p>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO CARDS DE PLANOS: Básico, Profissional e Empresa                  -->
        <!-- ===================================================================== -->
        <section class="planos-container" id="planos">
            
            <!-- Card: Plano Básico -->
            <article class="plano-card">
                <div class="plano-header">
                    <img src="${pageContext.request.contextPath}/assets/planos/icon-p-basico.png" alt="Ícone Plano Básico" class="plano-icon">
                    <h2>Plano Básico</h2>
                    <p class="plano-descricao">Ideal para quem está começando a descartar óleo corretamente.</p>
                </div>
                
                <div class="plano-preco">
                    <span class="moeda">R$</span>
                    <span class="valor">19,90</span>
                    <span class="periodo">/mês</span>
                </div>

                <ul class="plano-recursos">
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-gota.png" alt="" aria-hidden="true"> Até 5 litros por coleta</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-calendario.png" alt="" aria-hidden="true"> Agendamento online</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-acompanhamento.png" alt="" aria-hidden="true"> Acompanhamento básico de impacto</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-e-mail.png" alt="" aria-hidden="true"> Suporte por e-mail</li>
                </ul>

                <a href="#assinar-basico" class="btn-plano btn-outline">Começar agora</a>
            </article>

            <!-- Card: Plano Profissional (Destaque) -->
            <article class="plano-card plano-destaque">
                <div class="plano-header">
                    <img src="${pageContext.request.contextPath}/assets/planos/icon-p-empresa.png" alt="Ícone Plano Profissional" class="plano-icon">
                    <h2>Plano Profissional</h2>
                    <p class="plano-descricao">Ideal para quem quer mais praticidade e acompanhar o impacto.</p>
                </div>

                <div class="plano-preco">
                    <span class="moeda">R$</span>
                    <span class="valor">39,90</span>
                    <span class="periodo">/mês</span>
                </div>

                <ul class="plano-recursos">
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-gota.png" alt="" aria-hidden="true"> Até 20 litros por coleta</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-calendario.png" alt="" aria-hidden="true"> Agendamentos ilimitados</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-acompanhamento.png" alt="" aria-hidden="true"> Acompanhamento completo de impacto</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-relatorios.png" alt="" aria-hidden="true"> Relatórios mensais</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-suporte.png" alt="" aria-hidden="true"> Suporte prioritário</li>
                </ul>

                <a href="#assinar-profissional" class="btn-plano btn-filled">Escolher plano</a>
            </article>

            <!-- Card: Plano Empresa -->
            <article class="plano-card">
                <div class="plano-header">
                    <img src="${pageContext.request.contextPath}/assets/planos/icon-p-profissional.png" alt="Ícone Plano Empresa" class="plano-icon">
                    <h2>Plano Empresa</h2>
                    <p class="plano-descricao">Ideal para empresas que desejam gerar ainda mais impacto.</p>
                </div>

                <div class="plano-preco">
                    <span class="moeda">R$</span>
                    <span class="valor">79,90</span>
                    <span class="periodo">/mês</span>
                </div>

                <ul class="plano-recursos">
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-gota.png" alt="" aria-hidden="true"> Até 100 litros por coleta</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-calendario.png" alt="" aria-hidden="true"> Agendamentos ilimitados</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-relatorios.png" alt="" aria-hidden="true"> Relatórios personalizados</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-certifiados.png" alt="" aria-hidden="true"> Certificados de descarte</li>
                    <li><img src="${pageContext.request.contextPath}/assets/planos/icon-suporte.png" alt="" aria-hidden="true"> Suporte dedicado</li>
                </ul>

                <a href="#assinar-empresa" class="btn-plano btn-outline">Para empresas</a>
            </article>

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
    <!-- RODAPÉ (FOOTER): Links de navegação, contato e direitos autorais          -->
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