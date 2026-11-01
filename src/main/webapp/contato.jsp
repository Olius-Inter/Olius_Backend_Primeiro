<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <title>Olius - Sobre o Projeto e Equipe</title>

    <!-- Arquivos de estilos CSS globais e específicos da página de Contato/Sobre -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contato.css">
</head>
<body>

    <!-- ===================================================================== -->
    <!-- CABEÇALHO (HEADER): Logo, iluminação de fundo e navegação principal    -->
    <!-- ===================================================================== -->
    <header class="header">
        <div class="luz-amarela"></div>
        <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
            <img class="logo-icon" src="${pageContext.request.contextPath}/assets/global/logo-normal.svg" alt="Logotipo Olius">
            <span class="logo-texto">Olius</span>
        </a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/como_funciona.jsp">Como funciona</a>
            <a href="${pageContext.request.contextPath}/planos.jsp">Planos</a>
            <a href="${pageContext.request.contextPath}/contato.jsp" class="active">Sobre</a>
            <a href="#agendar" class="btn-agendar">Agendar coleta</a>
            <a href="${pageContext.request.contextPath}/login?sair=1">Sair</a>
        </nav>
    </header>

    <main>
        <!-- ===================================================================== -->
        <!-- SEÇÃO SOBRE O PROJETO: Mockups de apresentação e texto institucional  -->
        <!-- ===================================================================== -->
        <section class="conheca-o-projeto" id="sobre">
 
            <div class="projeto-header">
                <h2>Conheça o <span class="projeto-destaque">projeto</span></h2>
                <p class="subtitulo">Entenda como a Olius nasceu e o que queremos transformar</p>
            </div>

            <!-- Mockups das telas (esquerda) e texto explicativo (direita) -->
            <div class="projeto-conteudo">
                <div class="projeto-mockups">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-web.png" alt="Mockup da plataforma Olius no computador" class="mockup-notebook">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-mob.png" alt="Mockup do aplicativo Olius no celular" class="mockup-mobile">
                </div>
                
                <div class="descricao-projeto">
                    <p>Criada por alunos do Instituto J&F – Tech, a Olius é uma plataforma digital que facilita o descarte correto de óleo usado, oferecendo soluções de coleta, rastreamento e dados de impacto em tempo real.</p>
                    <p>Surgimos ao observar a dificuldade de acesso ao descarte adequado de resíduos, tendo o óleo usado como um dos materiais mais comuns e prejudiciais quando descartado de forma incorreta.</p>
                    <p>Unimos tecnologia, educação e logística reversa para transformar um resíduo poluente em novas oportunidades para o planeta e para as pessoas.</p>
                    <p>Nossa missão é conectar pessoas, empresas e comunidades em torno do descarte correto de óleo usado, gerando impacto ambiental, social e econômico.</p>
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO PILARES: Valores fundamentais da iniciativa                      -->
        <!-- ===================================================================== -->
        <section class="pilares">
            <h2 class="nossos-pilares">Nossos Pilares</h2>
            
            <div class="grid-pilares">
                <article class="pilar-card">
                    <img src="${pageContext.request.contextPath}/assets/contato/icon-sustentabilidade.png" alt="Ícone Sustentabilidade">
                    <h3>Sustentabilidade</h3>
                    <p>Cuidamos do planeta em ações que reduzem a poluição e preservam recursos.</p>
                </article>

                <article class="pilar-card">
                    <img src="${pageContext.request.contextPath}/assets/contato/icon-inovacao.png" alt="Ícone Inovação">
                    <h3>Inovação</h3>
                    <p>Usamos tecnologia para criar soluções inteligentes, simples e acessíveis.</p>
                </article>

                <article class="pilar-card">
                    <img src="${pageContext.request.contextPath}/assets/contato/icon-impacto-social.png" alt="Ícone Impacto Social">
                    <h3>Impacto Social</h3>
                    <p>Geramos oportunidades, educamos comunidades e transformamos realidades.</p>
                </article>

                <article class="pilar-card">
                    <img src="${pageContext.request.contextPath}/assets/contato/icon-transparencia.png" alt="Ícone Transparência">
                    <h3>Transparência</h3>
                    <p>Compartilhamos dados e resultados para construir confiança e gerar valor.</p>
                </article>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO EQUIPE: Carrossel com integrantes, frases e links de portfólio  -->
        <!-- ===================================================================== -->
        <section class="conheca-a-equipe">
            <div class="textos">
                <h2 class="conheca-a-equipe-container">
                    <span>Conheça a </span>
                    <span class="equipe">equipe</span>
                </h2>
                <p class="somos-um-time">Somos um time apaixonado por inovação, sustentabilidade e impacto positivo</p>
            </div>

            <div class="carrossel-equipe">
                <!-- Botão de navegação para a esquerda -->
                <button class="nav-btn prev-btn" id="prevBtn" aria-label="Anterior">&#10094;</button>
                
                <!-- Container rolável com os cards dos integrantes -->
                <div class="equipe-cards-container">

                <!-- Bruna Furlaneto -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/bruna.png" alt="Foto da Bruna Furlaneto">
                        <h3>Bruna Furlaneto</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">1° ano</span>
                        <blockquote>“Não deixe que seus medos ocupem o lugar dos seus sonhos.”<br><cite>Walt Disney</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon1" aria-label="GitHub da Bruna"></button>
                    </article>

                <!-- Isabelle Vitória -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/isabelle.png" alt="Foto da Isabelle Vitória">
                        <h3>Isabelle Vitória</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">1° ano</span>
                        <blockquote>“Afinal, aquilo que amamos sempre será parte de nós”<br><cite>Harry Potter</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon2" aria-label="GitHub da Isabelle"></button>
                    </article>

                <!-- Daniel Lima -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/daniel.png" alt="Foto do Daniel Lima">
                        <h3>Daniel Lima</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">1° ano</span>
                        <blockquote>“Limites são só questão de coragem”<br><cite>Ayrton Senna</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon" aria-label="GitHub do Daniel"></button>
                    </article>

                <!-- Enzo Rodrigues -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/enzo.png" alt="Foto do Enzo Rodrigues">
                        <h3>Enzo Rodrigues</h3>
                        <span class="cargo">Back e Dados</span>
                        <span class="ano">1° ano</span>
                        <blockquote>“Não fique olhando para o relógio, faça como ele: mexa-se.”</blockquote>
                        <button class="github-btn" id="rectangleIcon3" aria-label="GitHub do Enzo"></button>
                    </article>

                <!-- Guilherme Rocha -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/guilherme-o.png" alt="Foto do Guilherme Rocha">
                        <h3>Guilherme Rocha</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">1° ano</span>
                        <blockquote>“O sucesso é ir de fracasso em fracasso sem perder o entusiasmo”</blockquote>
                        <button class="github-btn" id="rectangleIcon4" aria-label="GitHub do Guilherme Rocha"></button>
                    </article>

                <!-- Caio Mezini -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/caio.png" alt="Foto do Caio Mezini">
                        <h3>Caio Mezini</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>“Racing is the pursuit of a lap that will never be perfect.”</blockquote>
                        <button class="github-btn" id="rectangleIcon5" aria-label="GitHub do Caio"></button>
                    </article>

                <!-- David Reche -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/david.png" alt="Foto do David Reche">
                        <h3>David Reche</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>"Uma vida sem reflexão não vale a pena ser vivida."<br><cite>Sócrates</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon6" aria-label="GitHub do David"></button>
                    </article>

                <!-- Erick Santos -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/erick.png" alt="Foto do Erick Santos">
                        <h3>Erick Santos</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>“Eles podem, você não”<br><cite>Paulo</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon7" aria-label="GitHub do Erick"></button>
                    </article>

                <!-- Guilherme Marcato -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/guilherme-m.png" alt="Foto do Guilherme Marcato">
                        <h3>Guilherme Marcato</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>“A sua alma pertencerá apenas a você"<br><cite>Cruzada</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon8" aria-label="GitHub do Guilherme Marcato"></button>
                    </article>

                <!-- Matheus Rezende -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/matheus.png" alt="Foto do Matheus Rezende">
                        <h3>Matheus Rezende</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>"O caos não é um buraco. O caos é uma escada."<br><cite>Petyr Baelish</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon9" aria-label="GitHub do Matheus"></button>
                    </article>

                <!-- Pedro Yada -->
                    <article class="membro-card">
                        <img class="foto-membro" src="${pageContext.request.contextPath}/assets/contato/pedro.png" alt="Foto do Pedro Yada">
                        <h3>Pedro Yada</h3>
                        <span class="cargo">Full Stack</span>
                        <span class="ano">2° ano</span>
                        <blockquote>“Obsession is gonna beat talent every time. You've got all the talent in the world, but are you obsessed?”<br><cite>Hustle</cite></blockquote>
                        <button class="github-btn" id="rectangleIcon10" aria-label="GitHub do Pedro Yada"></button>
                    </article>
                </div>

                <!-- Botão de navegação para a direita -->
                <button class="nav-btn next-btn" id="nextBtn" aria-label="Próximo">&#10095;</button>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO BANNER DE UNIÃO: Mensagem institucional de encerramento        -->
        <!-- ===================================================================== -->
        <section class="juntos-pelo-futuro-banner">
            <div class="juntos-conteudo">
                <div class="icone-grupo">
                    <img src="${pageContext.request.contextPath}/assets/contato/icon-uniao.png" alt="Ícone União">
                </div>
                <div class="juntos-texto">
                    <h2>Juntos por um futuro mais sustentável</h2>
                    <p>Acreditamos que a colaboração e a inovação são as chaves para transformar óleo usado em oportunidade que melhoram o mundo.</p>
                </div>
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

            <!-- Imagem decorativa do respingo de óleo -->
            <img src="${pageContext.request.contextPath}/assets/global/oleo.png" alt="Respingo de óleo decorativo" class="cta-imagem-oleo">
        </section>
    </main>

    <!-- ========================================================================= -->
    <!-- RODAPÉ (FOOTER): Links de navegação, contatos e créditos institucionais   -->
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

    <!-- ========================================================================= -->
    <!-- SCRIPTS (JAVASCRIPT): Lógica de Interatividade e Funcionalidades          -->
    <!-- ========================================================================= -->
    <script>
        /**
         * Mapeamento de IDs dos botões HTML para os respectivos links dos perfis no GitHub.
         * Garante a associação individual de cada membro da equipe ao seu repositório correspondente.
         */
        const githubLinks = {
            "rectangleIcon": "https://github.com/eudanixzz",
            "rectangleIcon1": "https://github.com/brunafurlaneto",
            "rectangleIcon2": "https://github.com/IsabelleVitoria22",
            "rectangleIcon3": "https://github.com/enzorodriguesismart-rgb",
            "rectangleIcon4": "https://github.com/Gui-bck",
            "rectangleIcon5": "https://github.com/caiomezini",
            "rectangleIcon6": "https://github.com/davidreche",
            "rectangleIcon7": "https://github.com/derrixs",
            "rectangleIcon8": "https://github.com/Guilherme38-ultra",
            "rectangleIcon9": "https://github.com/MatheussRezende",
            "rectangleIcon10": "https://github.com/yada071"
        };

        /**
         * Associa um evento de clique a cada botão do GitHub listado no dicionário acima,
         * abrindo o perfil correspondente em uma nova aba com segurança (_blank).
         */
        Object.keys(githubLinks).forEach(id => {
            const btn = document.getElementById(id);
            if (btn) {
                btn.addEventListener("click", function () {
                    window.open(githubLinks[id], "_blank");
                });
            }
        });

        /**
         * Controle de rolagem horizontal suave (Carrossel de Membros da Equipe)
         * Utiliza o deslocamento baseado na largura visível do container através das setas laterais.
         */
        const container = document.querySelector('.equipe-cards-container');
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');

        if (container && prevBtn && nextBtn) {
            // Rola para a direita ao clicar na seta "Próximo"
            nextBtn.addEventListener('click', () => {
                container.scrollBy({ left: container.clientWidth, behavior: 'smooth' });
            });

            // Rola para a esquerda ao clicar na seta "Anterior"
            prevBtn.addEventListener('click', () => {
                container.scrollBy({ left: -container.clientWidth, behavior: 'smooth' });
            });
        }
    </script>
</body>
</html>