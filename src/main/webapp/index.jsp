<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Olius - Inicial</title>

    <!-- Arquivos de estilos CSS globais e específicos da página inicial -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>

    <!-- ========================================================================= -->
    <!-- CABEÇALHO (HEADER): Logo e navegação principal do site                      -->
    <!-- ========================================================================= -->
    <header class="header">
        <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
            <img class="logo-icon" src="${pageContext.request.contextPath}/assets/global/logo-normal.svg" alt="Logotipo Olius">
            <span class="logo-texto">Olius</span>
        </a>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/como_funciona.jsp">Como funciona</a>
            <a href="${pageContext.request.contextPath}/planos.jsp">Planos</a>
            <a href="${pageContext.request.contextPath}/contato.jsp">Sobre</a>
            <a href="#agendar" class="btn-agendar">Agendar coleta</a>
            <a href="${pageContext.request.contextPath}/login?sair=1">Sair</a>
        </nav>
    </header>

    <main>
        <!-- ===================================================================== -->
        <!-- SEÇÃO HERO: Apresentação principal, chamadas para ação e mockups      -->
        <!-- ===================================================================== -->
        <section class="inicio">
            <!-- Coluna Esquerda: Títulos, descrições, botões e diferenciais -->
            <div class="hero-content">
                <h1>Tecnologia que transforma óleo usado em <span class="highlight">impacto positivo.</span></h1>
                <p class="hero-description">
                    Conectamos empresas, cidadãos e pontos de coleta para tornar o descarte de óleo de cozinha mais simples, eficiente e sustentável.
                </p>
                
                <div class="hero-buttons">
                    <a href="#agendar" class="btn-primary">Agendar coleta</a>
                    <a href="#solucoes" class="btn-secondary">Conheça a plataforma</a>
                </div>

                <div class="hero-features">
                    <div class="feature-item">
                        <img src="${pageContext.request.contextPath}/assets/index/topicos.png" alt="" class="feature-icon">
                        <div>
                            <strong>Destino correto</strong>
                            <p>Transformado em biodiesel e outros produtos</p>
                        </div>
                    </div>
                    <div class="feature-item">
                        <img src="${pageContext.request.contextPath}/assets/index/topicos.png" alt="" class="feature-icon">
                        <div>
                            <strong>Impacto real</strong>
                            <p>Acompanhe seus indicadores ambientais em tempo real</p>
                        </div>
                    </div>
                    <div class="feature-item">
                        <img src="${pageContext.request.contextPath}/assets/index/topicos.png" alt="" class="feature-icon">
                        <div>
                            <strong>Transparência total</strong>
                            <p>Do descarte à destinação final, tudo rastreado.</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Coluna Direita: Mockups visuais (Notebook e Celular) -->
            <div class="hero-mockup">
                <div class="projeto-mockups">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-web.png" alt="Mockup Notebook" class="mockup-notebook">
                    <img src="${pageContext.request.contextPath}/assets/global/mock-mob.png" alt="Mockup Celular" class="mockup-mobile">
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO DESTAQUES: Cards com os principais benefícios do descarte       -->
        <!-- ===================================================================== -->
        <section class="destaques">
            <div class="card-destaque">
                <div class="card-icon-wrapper">
                    <img src="${pageContext.request.contextPath}/assets/index/proteja.png" alt="Protege a água e o solo">
                </div>
                <div class="card-conteudo">
                    <h3>Protege a água e o solo</h3>
                    <p>1 litro de óleo pode poluir até 25 mil litros de água.</p>
                </div>
            </div>

            <div class="card-destaque">
                <div class="card-icon-wrapper">
                    <img src="${pageContext.request.contextPath}/assets/index/reduz.png" alt="Reduz custos e ineficiências">
                </div>
                <div class="card-conteudo">
                    <h3>Reduz custos e ineficiências</h3>
                    <p>Rotas inteligentes geram economia e menos emissão de poluentes.</p>
                </div>
            </div>

            <div class="card-destaque">
                <div class="card-icon-wrapper">
                    <img src="${pageContext.request.contextPath}/assets/index/gera-impacrto.png" alt="Gera impacto positivo real">
                </div>
                <div class="card-conteudo">
                    <h3>Gera impacto positivo real</h3>
                    <p>Mais sustentabilidade, conscientização e futuro.</p>
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO COMO FUNCIONA: Passo a passo do processo da plataforma          -->
        <!-- ===================================================================== -->
        <section class="como-funciona" id="como-funciona">
            <h2>Como funciona</h2>

            <div class="passos-container">
                <!-- Linha conectora visual ao fundo -->
                <div class="linha-passos"></div>

                <div class="passos-lista">
                    <!-- Passo 1 -->
                    <div class="passo-item">
                        <div class="passo-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/passo1.png" alt="Passo 1" class="passo-icon">
                        </div>
                        <span class="passo-numero">1</span>
                        <p>Cadastre seu estabelecimento ou ponto de coleta.</p>
                    </div>

                    <!-- Passo 2 -->
                    <div class="passo-item">
                        <div class="passo-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/passo2.png" alt="Passo 2" class="passo-icon">
                        </div>
                        <span class="passo-numero">2</span>
                        <p>Informe a quantidade de óleo acumulado.</p>
                    </div>

                    <!-- Passo 3 -->
                    <div class="passo-item">
                        <div class="passo-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/passo3.png" alt="Passo 3" class="passo-icon">
                        </div>
                        <span class="passo-numero">3</span>
                        <p>Receba a previsão ideal de coleta.</p>
                    </div>

                    <!-- Passo 4 -->
                    <div class="passo-item">
                        <div class="passo-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/passo4.png" alt="Passo 4" class="passo-icon">
                        </div>
                        <span class="passo-numero">4</span>
                        <p>Agende a coleta de forma prática.</p>
                    </div>

                    <!-- Passo 5 -->
                    <div class="passo-item">
                        <div class="passo-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/passo5.png" alt="Passo 5" class="passo-icon">
                        </div>
                        <span class="passo-numero">5</span>
                        <p>Acompanhe o impacto e faça diferença.</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO IMPACTO EM TEMPO REAL: Métricas e indicadores alcançados        -->
        <!-- ===================================================================== -->
        <section class="impacto-tempo-real">
            <h2>Veja seu impacto em tempo real</h2>
            <p class="subtitulo">Resultados que mostram a força da sua atitude</p>

            <div class="metricas-grid">
                <!-- Card 1: Água preservada -->
                <div class="metrica-card">
                    <img src="${pageContext.request.contextPath}/assets/index/agua.svg" alt="" class="card-bg-icon">
                    <div class="metrica-conteudo">
                        <strong class="valor">120 milhões</strong>
                        <span class="rotulo">Litros de água preservados</span>
                    </div>
                </div>

                <!-- Card 2: CO2 evitado -->
                <div class="metrica-card">
                    <img src="${pageContext.request.contextPath}/assets/index/co2.svg" alt="" class="card-bg-icon">
                    <div class="metrica-conteudo">
                        <strong class="valor">56 toneladas</strong>
                        <span class="rotulo">De CO₂ evitadas</span>
                    </div>
                </div>

                <!-- Card 3: Óleo coletado -->
                <div class="metrica-card">
                    <img src="${pageContext.request.contextPath}/assets/index/litros-oleo.svg" alt="" class="card-bg-icon">
                    <div class="metrica-conteudo">
                        <strong class="valor">38 mil litros</strong>
                        <span class="rotulo">De óleo coletados corretamente</span>
                    </div>
                </div>

                <!-- Card 4: Certificação -->
                <div class="metrica-card">
                    <img src="${pageContext.request.contextPath}/assets/index/certificado7.png" alt="" class="card-bg-icon">
                    <div class="metrica-conteudo">
                        <strong class="valor">Parceiro Ouro</strong>
                        <span class="rotulo">Certificação máxima em sustentabilidade</span>
                    </div>
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO SOLUÇÕES: Divisão de serviços para Empresas (B2B) e Cidadãos    -->
        <!-- ===================================================================== -->
        <section class="solucoes" id="solucoes">
            <!-- Card 1: Para empresas -->
            <div class="solucao-card">
                <div class="solucao-conteudo">
                    <h3>Para empresas</h3>
                    <p>Solução completa para negócios que querem ser mais sustentáveis</p>
                    <ul>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Gestão de coleta otimizada</span>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Redução de custos logísticos</span>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Relatórios de impacto ambiental</span>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Certificação de sustentabilidade</span>
                        </li>
                    </ul>
                    <a href="#saiba-mais" class="btn-solucao">Saiba mais</a>
                </div>
                <div class="solucao-imagem">
                    <img src="${pageContext.request.contextPath}/assets/index/foto-b2b.png" alt="Para empresas">
                </div>
            </div>

            <!-- Card 2: Para cidadãos -->
            <div class="solucao-card">
                <div class="solucao-conteudo">
                    <h3>Para cidadãos</h3>
                    <p>Descarte correto, fácil e perto de você. Pequenas atitudes, grandes mudanças.</p>
                    <ul>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Encontre pontos de entrega</span>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Acompanhe seu impacto</span>
                        </li>
                        <li>
                            <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="item-icon">
                            <span>Participe de um movimento sustentável</span>
                        </li>
                    </ul>
                    <a href="#encontre-ponto" class="btn-solucao btn-laranja">Encontre um ponto</a>
                </div>
                <div class="solucao-imagem">
                    <img src="${pageContext.request.contextPath}/assets/index/foto-b2c.png" alt="Para cidadãos">
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO MAPA DE PONTOS: Busca por CEP e exibição do mapa interativo     -->
        <!-- ===================================================================== -->
        <section class="secao-mapa-container" id="pontos">
            <div class="mapa-card-principal">
                <!-- Coluna Esquerda: Informações, input de busca por CEP e mini cards -->
                <div class="mapa-info-lateral">
                    <h2>Encontre um ponto de coleta <span class="highlight">perto de você</span></h2>
                    <p class="mapa-subtitulo">Descubra o local mais próximo e agende sua coleta de forma rápida e simples.</p>
                    
                    <div class="cep-busca-box">
                        <input type="text" placeholder="Digite seu CEP" class="input-cep">
                        <button type="button" class="btn-buscar-cep">Buscar</button>
                    </div>

                    <div class="info-mini-cards">
                        <div class="mini-card">
                            <div class="mini-icon-wrapper">
                                <img src="${pageContext.request.contextPath}/assets/index/pontos-ativos.svg" alt="" class="mini-icon">
                            </div>
                            <div>
                                <strong>25</strong>
                                <strong>pontos ativos</strong>
                            </div>
                        </div>

                        <div class="mini-card">
                            <div class="mini-icon-wrapper">
                                <img src="${pageContext.request.contextPath}/assets/index/coletaresidencial.png" alt="" class="mini-icon">
                            </div>
                            <div>
                                <strong>Coleta</strong>
                                <strong>residencial</strong>
                            </div>
                        </div>

                        <div class="mini-card">
                            <div class="mini-icon-wrapper">
                                <img src="${pageContext.request.contextPath}/assets/index/coletaempresarial.png" alt="" class="mini-icon">
                            </div>
                            <div>
                                <strong>Coleta</strong>
                                <strong>empresarial</strong>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Coluna Direita: Mapa incorporado do Google Maps -->
                <div class="mapa">
                    <iframe
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3657.1973656113697!2d-46.65657252373979!3d-23.561349161595155!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x94ce59c8da0aa315%3A0xd59f437521262d11!2sAv.%20Paulista%2C%20S%C3%A3o%20Paulo%2C%20SP!5e0!3m2!1spt-BR!2sbr!4v1700000000000!5m2!1spt-BR!2sbr"
                        loading="lazy"
                        allowfullscreen
                        title="Mapa da Olius">
                    </iframe>
                </div>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO DESTINAÇÃO DO ÓLEO: O que é feito com o material coletado       -->
        <!-- ===================================================================== -->
        <section class="destinacao-oleo">
            <h2>O que acontece com o <span class="highlight">óleo?</span></h2>
            <p>Após o descarte correto, o óleo usado se transforma em novos recursos que geram impacto positivo</p>
            
            <div class="produtos-grid">
                <article class="produto-card">
                    <div class="produto-icon-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/index/biodiesel.png" alt="Biodiesel" class="produto-icon">
                    </div>
                    <h3>Biodiesel</h3>
                    <p>O óleo é processado e transformado em combustível renovável, menos poluente e mais sustentável.</p>
                </article>

                <article class="produto-card">
                    <div class="produto-icon-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/index/sabao.png" alt="Sabão" class="produto-icon">
                    </div>
                    <h3>Sabão</h3>
                    <p>Usado na fabricação de sabões e produtos de limpeza, ajudando a reduzir o impacto ambiental.</p>
                </article>

                <article class="produto-card">
                    <div class="produto-icon-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/index/velas.png" alt="Velas" class="produto-icon">
                    </div>
                    <h3>Velas</h3>
                    <p>O óleo pode virar matéria-prima para velas artesanais e produtos para uso doméstico.</p>
                </article>

                <article class="produto-card">
                    <div class="produto-icon-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/index/outros-produtos.png" alt="Outros produtos" class="produto-icon">
                    </div>
                    <h3>Outros produtos</h3>
                    <p>Também pode ser utilizado na produção de lubrificantes e outros produtos industriais.</p>
                </article>
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- SEÇÃO APLICATIVO: Recursos do app móvel para acompanhamento            -->
        <!-- ===================================================================== -->
        <section class="app-acompanhamento">
            <div class="app-info">
                <h2>Acompanhe seu impacto <span class="highlight">na palma da mão.</span></h2>
                <p>Nosso aplicativo facilita todo o processo e mostra o impacto positivo que você gera a cada coleta.</p>
                
                <ul>
                    <li>
                        <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="check-icon">
                        <span>Agende coletas</span>
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="check-icon">
                        <span>Veja seu impacto em tempo real</span>
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="check-icon">
                        <span>Encontre pontos de coleta</span>
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="check-icon">
                        <span>Receba notificações</span>
                    </li>
                    <li>
                        <img src="${pageContext.request.contextPath}/assets/index/tipos-icon.png" alt="" class="check-icon">
                        <span>Ganhe pontos e suba de nível</span>
                    </li>
                </ul>
            </div>
            
            <div class="app-preview">
                <img src="${pageContext.request.contextPath}/assets/index/mockupsolius.png" alt="Telas do aplicativo móvel Olius">
            </div>
        </section>

        <!-- ===================================================================== -->
        <!-- BLOCO UNIFICADO: Vantagens, Divisor Decorativo e Certificações        -->
        <!-- ===================================================================== -->
        <div class="vantagens-certificacoes-wrapper">

            <!-- Seção de Vantagens -->
            <section id="vantagens" class="secao-lado-a-lado">
                <div class="coluna-texto">
                    <h2>Vantagens do <span class="highlight">Olius</span></h2>
                    <p>Nossa tecnologia oferece praticidade, segurança e impacto positivo para você e para o planeta.</p>
                </div>

                <div class="coluna-cards vantagens-grid">
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/coleta.png" alt="Coleta rápida e prática" class="vantagem-icon">
                        </div>
                        <h3>Coleta rápida e prática</h3>
                        <p>Agende coletas de forma simples e rápida, quando e onde precisar.</p>
                    </div>
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/impacto.png" alt="Impacto ambiental real" class="vantagem-icon">
                        </div>
                        <h3>Impacto ambiental real</h3>
                        <p>Cada litro de óleo coletado evita a poluição de milhares de litros de água.</p>
                    </div>
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/dados-em-tempo.png" alt="Dados em tempo real" class="vantagem-icon">
                        </div>
                        <h3>Dados em tempo real</h3>
                        <p>Acompanhe suas coletas e impacto ambiental em uma dashboard intuitiva.</p>
                    </div>
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/certificado.png" alt="Certificado de sustentabilidade" class="vantagem-icon">
                        </div>
                        <h3>Certificado de sustentabilidade</h3>
                        <p>Receba certificados que comprovam sua contribuição para o meio ambiente.</p>
                    </div>
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/economia.png" alt="Economia e eficiência" class="vantagem-icon">
                        </div>
                        <h3>Economia e eficiência</h3>
                        <p>Reduza custos operacionais e contribua para um futuro mais sustentável.</p>
                    </div>
                    <div class="vantagem-card">
                        <div class="vantagem-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/conexao.png" alt="Conexão que transforma" class="vantagem-icon">
                        </div>
                        <h3>Conexão que transforma</h3>
                        <p>Conectamos empresas e coletores para um ciclo sustentável e colaborativo.</p>
                    </div>
                </div>
            </section>

            <!-- Imagem de respingo de óleo decorativa (Divisor de seções) -->
            <div class="respingo-oleo-container">
                <img src="${pageContext.request.contextPath}/assets/index/quebrapag2.png" alt="" class="respingo-oleo-img">
            </div>

            <!-- Seção de Certificações e ODS -->
            <section id="certificacoes" class="secao-lado-a-lado">
                <div class="coluna-texto">
                    <h2>Certificações<br>e compromissos</h2>
                    <p>Seguimos padrões rigorosos para garantir segurança, qualidade e um impacto real no meio ambiente.</p>
                </div>

                <div class="coluna-cards certificacoes-grid">
                    <div class="certificacao-card">
                        <div class="cert-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/ods12.png" alt="ODS 12" class="cert-icon">
                        </div>
                        <strong>ODS 12</strong>
                        <p>Consumo e Produção Responsáveis</p>
                        <div class="card-line"></div>
                    </div>
                    <div class="certificacao-card">
                        <div class="cert-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/carbono-reduzido.png" alt="Carbono Reduzido" class="cert-icon">
                        </div>
                        <strong>Carbono Reduzido</strong>
                        <p>Operação alinhada à redução de emissões</p>
                        <div class="card-line"></div>
                    </div>
                    <div class="certificacao-card">
                        <div class="cert-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/certificado.png" alt="Reciclagem Certificada" class="cert-icon">
                        </div>
                        <strong>Reciclagem Certificada</strong>
                        <p>Processos rastreáveis e ambientalmente responsáveis</p>
                        <div class="card-line"></div>
                    </div>
                    <div class="certificacao-card">
                        <div class="cert-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/index/coleta-segura.png" alt="Coleta Segura" class="cert-icon">
                        </div>
                        <strong>Coleta Segura</strong>
                        <p>Conformidade com normas ambientais e de segurança</p>
                        <div class="card-line"></div>
                    </div>
                </div>
            </section>

        </div>

        <!-- ===================================================================== -->
        <!-- SEÇÃO FAQ: Perguntas frequentes estruturadas com tags <details>       -->
        <!-- ===================================================================== -->
        <section id="faq" class="faq-secao">
            <div class="faq-header">
                <h2>Ainda com Dúvidas?</h2>
            </div>

            <div class="faq-accordion">
                <details class="faq-item">
                    <summary>
                        <span>1. O Olius fornece certificado de destinação correta do óleo?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>A plataforma disponibiliza um certificado de destinação correta para empresas e parceiros que realizam o descarte por meio da plataforma. O documento comprova que o óleo foi encaminhado para a destinação adequada e pode ser utilizado como registro de responsabilidade ambiental.</p>
                </details>

                <details class="faq-item">
                    <summary>
                        <span>2. Como acompanho meu impacto ambiental em tempo real?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>Após realizar o descarte ou a coleta, os dados são registrados no aplicativo. O usuário pode acompanhar informações como quantidade de óleo destinada corretamente, estimativa de impacto ambiental evitado e sua contribuição para a reciclagem, por meio de um painel atualizado conforme os registros são realizados.</p>
                </details>

                <details class="faq-item">
                    <summary>
                        <span>3. Ganho algum tipo de benefício ou recompensa ao descartar meu óleo com o Olius?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>A Olius possui um sistema de benefícios baseado na participação do usuário. Conforme a quantidade de óleo destinada corretamente e a frequência de participação aumentam, o usuário pode avançar entre níveis, como Bronze, Prata e Ouro, desbloqueando benefícios e reconhecimentos dentro da plataforma.</p>
                </details>

                <details class="faq-item">
                    <summary>
                        <span>4. Qual é a quantidade mínima de óleo para solicitar uma coleta corporativa?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>Após pesquisas, estabelecemos uma quantidade mínima de 20 litros para solicitar uma coleta corporativa. Quantidades menores podem ser direcionadas aos pontos de coleta parceiros, tornando a logística mais eficiente.</p>
                </details>

                <details class="faq-item">
                    <summary>
                        <span>5. Quais documentos são necessários para validar meu cadastro como coletor?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>Para o cadastro de um coletor parceiro, podem ser solicitados documentos que comprovem sua identidade e regularidade, como documento de identificação, CPF/CNPJ, comprovante de endereço e documentos ou autorizações relacionados à atividade de coleta, quando aplicáveis. A documentação pode variar conforme o tipo de coletor e a legislação.</p>
                </details>

                <details class="faq-item">
                    <summary>
                        <span>6. Como devo armazenar o óleo de cozinha usado antes de entregar?</span>
                        <span class="faq-icon"></span>
                    </summary>
                    <p>Espere o óleo esfriar completamente e armazene-o em garrafas PET limpas e secas, fechando bem a tampa para evitar vazamentos até o momento da entrega ou coleta.</p>
                </details>
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
    <!-- RODAPÉ (FOOTER): Informações de contato, links rápidos e créditos          -->
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
            
            <!-- Links de Navegação -->
            <nav class="footer-links" aria-label="Links do Rodapé">
                <h4>Navegação</h4>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Início</a></li>
                    <li><a href="${pageContext.request.contextPath}/como_funciona.jsp">Como funciona</a></li>
                    <li><a href="${pageContext.request.contextPath}/planos.jsp">Planos</a></li>
                    <li><a href="${pageContext.request.contextPath}/contato.jsp">Sobre nós</a></li>
                </ul>
            </nav>

            <!-- Informações de Contato -->
            <div class="footer-contato">
                <h4>Contato</h4>
                <ul>
                    <li><a href="mailto:oliusjef26@gmail.com">oliusjef26@gmail.com</a></li>
                    <li><a href="https://www.instagram.com/olius.app" target="_blank" rel="noopener noreferrer">@olius.app</a></li>
                    <li><span>São Paulo, SP - Brasil</span></li>
                </ul>
            </div>

            <!-- Créditos e Acesso Administrativo -->
            <div class="footer-proposito">
                <h4>Desenvolvido com propósito para um futuro sustentável.</h4>
                <p class="copyright">© 2026 Olius. Todos os direitos reservados.</p>
                <a href="${pageContext.request.contextPath}/adm/index.jsp" class="acesso-adm">Acesso ADM</a>
            </div>
        </div>
    </footer>

</body>
</html>