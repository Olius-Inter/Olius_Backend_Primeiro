<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Certificados - Olius</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/certificados.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <aside class="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/logo.png.png" alt="Logo Olius">
        </div>

        <div class="perfil">
            <i class="fa-solid fa-user"></i>
        </div>

        <nav>
            <a href="adm.jsp">Dashboard</a>
            <a href="empresas.jsp">Empresas</a>
            <a href="usuarios.jsp">Usuários</a>
            <a href="pev.jsp">PEV</a>
            <a href="motoristas.jsp">Motoristas</a>
            <a href="solicitacoes.jsp">Solicitações de coleta</a>
            <a href="coletas.jsp">Coletas realizadas</a>
            <a href="index.jsp" class="ativo">Certificados</a>
        </nav>
    </aside>

    <main class="conteudo">

        <header class="topo">
            <h1>Certificados</h1>
            <button class="adicionar">+ Adicionar Certificado</button>
        </header>

        <section class="tabela-container">
            <table>
                <thead>
                    <tr>
                        <th>Empresa</th>
                        <th>Data de emissão</th>
                        <th>Volume total</th>
                        <th>Arquivo PDF</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    <tr>
                        <td>Empresa B2B 01</td>
                        <td>30/09/2026</td>
                        <td>100 L</td>
                        <td>certificado_001.pdf</td>
                        <td class="acoes">
                            <button class="editar" title="Editar">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                            <button class="excluir" title="Excluir">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>

                    <tr>
                        <td>Empresa B2B 01</td>
                        <td>01/10/2026</td>
                        <td>250 L</td>
                        <td>certificado_002.pdf</td>
                        <td class="acoes">
                            <button class="editar" title="Editar">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                            <button class="excluir" title="Excluir">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>

                    <tr>
                        <td>EcoVerde</td>
                        <td>03/10/2026</td>
                        <td>150 L</td>
                        <td>certificado_003.pdf</td>
                        <td class="acoes">
                            <button class="editar" title="Editar">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                            <button class="excluir" title="Excluir">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>

                    <tr>
                        <td>BioCiclo</td>
                        <td>05/10/2026</td>
                        <td>200 L</td>
                        <td>certificado_004.pdf</td>
                        <td class="acoes">
                            <button class="editar" title="Editar">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </button>
                            <button class="excluir" title="Excluir">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>

    </main>

</body>
</html>
