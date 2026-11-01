<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/images/favicon-16x16.png">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="manifest" href="${pageContext.request.contextPath}/images/site.webmanifest">
    <title>Dashboard - Olius</title>

     <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adm.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/padrao.css">
</head>
<body data-base="${pageContext.request.contextPath}">

    <aside class="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png.png" alt="Logo Olius">
        </div>

        <div class="caixa"></div>

        <button type="button" class="botao-menu" aria-expanded="false" aria-controls="menuNavegacao">Menu</button>
        <nav id="menuNavegacao" aria-label="Menu principal">
            <a href="${pageContext.request.contextPath}/index.jsp" class="ativo" aria-current="page">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificações</a>
            <a href="${pageContext.request.contextPath}/login?sair=1">Sair</a>
        </nav>
    </aside>

    <main class="conteudo">

            <header class="topo">
                <div>
                    <h1>Dashboard</h1>
                    <p>Acompanhe os principais indicadores da Olius.</p>
                </div>
                <button type="button" class="filtro" id="atualizarDashboard">Atualizar</button>
            </header>
            <p id="mensagemDashboard" role="status">Carregando indicadores…</p>

            <!-- Indicadores -->
            <section class="indicadores" aria-label="Indicadores gerais">

                <!-- Empresas -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M3 21h18"/>
                            <path d="M5 21V7l8-4v18"/>
                            <path d="M13 10h6v11"/>
                            <path d="M8 9h1M8 13h1M8 17h1"/>
                            <path d="M16 14h1M16 17h1"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="empresas">…</h2>
                    <p class="descricao">Empresas cadastradas</p>
                </article>

                <!-- Usuários -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <circle cx="12" cy="8" r="4"/>
                            <path d="M5 21v-2a7 7 0 0 1 14 0v2"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="usuarios">…</h2>
                    <p class="descricao">Usuários cadastrados</p>
                </article>

                <!-- Pontos de coleta -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M20 10c0 5-8 12-8 12S4 15 4 10a8 8 0 1 1 16 0Z"/>
                            <circle cx="12" cy="10" r="2.5"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="pev">…</h2>
                    <p class="descricao">Pontos de coleta</p>
                </article>

                <!-- Motoristas -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <rect x="2" y="7" width="20" height="13" rx="2"/>
                            <path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2"/>
                            <path d="M2 12h20M10 12v2h4v-2"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="motoristas">…</h2>
                    <p class="descricao">Motoristas cadastrados</p>
                </article>

                <!-- Solicitações pendentes -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z"/>
                            <path d="M14 2v6h6M8 13h8M8 17h8"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="solicitacoes-coleta">…</h2>
                    <p class="descricao">Solicitações pendentes</p>
                </article>

                <!-- Coletas realizadas: símbolo de correto -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M5 12.5l4.5 4.5L19 7"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="coleta">…</h2>
                    <p class="descricao">Coletas realizadas</p>
                </article>

                <!-- Certificados -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M6 3h9l5 5v13H6a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2Z"/>
                            <path d="M14 3v6h6"/>
                            <path d="m8 15 2.5 2.5L16 12"/>
                        </svg>
                    </div>
                    <h2 class="numero" data-indicador="certificado">…</h2>
                    <p class="descricao">Certificações emitidos</p>
                </article>

            </section>

            <!-- Gráficos -->
            <section class="secao-graficos">
                <h2>Visão geral</h2>

                <div class="graficos">

                    <!-- Status das solicitações -->
                    <article class="painel">
                        <div class="painel-topo">
                            <div>
                                <h3>Status das solicitações</h3>
                                <p>Distribuição das solicitações de coleta</p>
                            </div>
                        </div>

                        <div class="rosca-area">
                            <div
                                class="grafico-rosca"
                                role="img"
                                aria-label="Carregando solicitações" style="background: #eeeae3">
                            </div>

                            <ul class="legenda"><li>Carregando solicitações…</li></ul>
                        </div>
                    </article>

                    <!-- Coletas por mês -->
                    <article class="painel grafico-crescimento">
                        <div class="painel-topo">
                            <div>
                                <h3>Coletas realizadas</h3>
                                <p>Solicitações realizadas por mês, conforme a data prevista</p><p id="resumoColetas">Carregando coletas…</p>
                            </div>

                            <select class="filtro" id="periodoColetas" aria-label="Período do gráfico" disabled>
                                <option value="6">Últimos 6 meses</option>
                                <option value="12">Últimos 12 meses</option>
                            </select>
                        </div>

                        <div class="grafico-linha">
                            <div class="eixo-y" aria-hidden="true">
                                <span>10</span>
                                <span>8</span>
                                <span>6</span>
                                <span>4</span>
                                <span>2</span>
                                <span>0</span>
                            </div>

                            <div class="area-grafico">
                                <div class="linhas-grade" aria-hidden="true">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </div>

                                <svg
                                    class="linha-svg"
                                    viewBox="0 0 600 160"
                                    preserveAspectRatio="none"
                                    role="img"
                                    aria-label="Carregando coletas">

                                    <polygon
                                        class="area-preenchida"
                                        points=""/>

                                    <polyline
                                        class="linha-dados"
                                        points=""/>

                                    <g class="marcadores"></g>
                                </svg>

                                <div class="meses" aria-hidden="true"></div>
                            </div>
                        </div>
                    </article>

                </div>
            </section>

        </main>

    <script src="${pageContext.request.contextPath}/js/dashboard.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/menu.js" defer></script>
</body>
    </html>
