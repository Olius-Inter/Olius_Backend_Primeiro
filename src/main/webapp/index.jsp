<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Olius</title>

     <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adm.css">
</head>
<body data-api="${pageContext.request.contextPath}/certificado" data-id="id_certificado" data-base="${pageContext.request.contextPath}/Certificados" data-columns="id_usuario_b2b,dt_emissao,volume_total,arquivo_pdf">

    <aside class="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

        <div class="caixa"></div>

        <nav>
            <a href="${pageContext.request.contextPath}/index.jsp" class="ativo" aria-current="page">Dashboard</a>
            <a href="${pageContext.request.contextPath}/Empresas/empresa.jsp">Empresas</a>
            <a href="${pageContext.request.contextPath}/Usuarios/usuarios.jsp">Usuários</a>
            <a href="${pageContext.request.contextPath}/PEV/pev.jsp">PEV</a>
            <a href="${pageContext.request.contextPath}/Motoristas/motoristas.jsp">Motoristas</a>
            <a href="${pageContext.request.contextPath}/Solicitações/solicitacoes.jsp">Solicitações de coleta</a>
            <a href="${pageContext.request.contextPath}/Coletas/coletas.jsp">Coletas realizadas</a>
            <a href="${pageContext.request.contextPath}/Certificados/certificados.jsp">Certificados</a>
        </nav>
    </aside>

    <main class="conteudo">

            <header class="topo">
                <div>
                    <h1>Dashboard</h1>
                    <p>Acompanhe os principais indicadores da Olius.</p>
                </div>
            </header>

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
                    <h2 class="numero">12</h2>
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
                    <h2 class="numero">28</h2>
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
                    <h2 class="numero">8</h2>
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
                    <h2 class="numero">6</h2>
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
                    <h2 class="numero">5</h2>
                    <p class="descricao">Solicitações pendentes</p>
                </article>

                <!-- Coletas realizadas: símbolo de correto -->
                <article class="card-indicador">
                    <div class="icone" aria-hidden="true">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M5 12.5l4.5 4.5L19 7"/>
                        </svg>
                    </div>
                    <h2 class="numero">4</h2>
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
                    <h2 class="numero">3</h2>
                    <p class="descricao">Certificados emitidos</p>
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
                                aria-label="Gráfico ilustrativo: duas solicitações pendentes, duas concluídas e uma em andamento">
                            </div>

                            <ul class="legenda">
                                <li>
                                    <span class="ponto ponto-amarelo"></span>
                                    <span>Pendentes<br><small>2 solicitações</small></span>
                                </li>
                                <li>
                                    <span class="ponto ponto-verde"></span>
                                    <span>Concluídas<br><small>2 solicitações</small></span>
                                </li>
                                <li>
                                    <span class="ponto ponto-laranja"></span>
                                    <span>Em andamento<br><small>1 solicitação</small></span>
                                </li>
                            </ul>
                        </div>
                    </article>

                    <!-- Coletas por mês -->
                    <article class="painel grafico-crescimento">
                        <div class="painel-topo">
                            <div>
                                <h3>Coletas realizadas</h3>
                                <p>Quantidade de coletas por mês</p>
                            </div>

                            <select class="filtro" aria-label="Período do gráfico">
                                <option>Últimos 6 meses</option>
                                <option>Últimos 12 meses</option>
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
                                    aria-label="Gráfico ilustrativo de coletas mensais">

                                    <polygon
                                        class="area-preenchida"
                                        points="0,130 120,110 240,120 360,65 480,85 600,30 600,160 0,160"/>

                                    <polyline
                                        class="linha-dados"
                                        points="0,130 120,110 240,120 360,65 480,85 600,30"/>

                                    <g class="marcadores">
                                        <circle cx="0" cy="130" r="4"/>
                                        <circle cx="120" cy="110" r="4"/>
                                        <circle cx="240" cy="120" r="4"/>
                                        <circle cx="360" cy="65" r="4"/>
                                        <circle cx="480" cy="85" r="4"/>
                                        <circle cx="600" cy="30" r="4"/>
                                    </g>
                                </svg>

                                <div class="meses" aria-hidden="true">
                                    <span>Mai</span>
                                    <span>Jun</span>
                                    <span>Jul</span>
                                    <span>Ago</span>
                                    <span>Set</span>
                                    <span>Out</span>
                                </div>
                            </div>
                        </div>
                    </article>

                </div>
            </section>

        </main>

    </body>
    </html>
