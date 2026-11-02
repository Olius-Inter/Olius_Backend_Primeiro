Sistema desenvolvido como parte de um projeto interdisciplinar do 1º ano, utilizando Java e PostgreSQL para estruturar o backend e gerenciar os dados da aplicação.

## Sobre o projeto

O OLIUS possui uma estrutura organizada para separar as responsabilidades do sistema, facilitando o desenvolvimento, a manutenção e o acesso aos dados.

O backend contém classes para conexão com o banco de dados, modelos de entidades, operações de acesso aos dados (DAO) e Servlets para receber e processar requisições HTTP.

## Tecnologias utilizadas

-  **Java** — linguagem de programação.
-  **Maven** — gerenciamento de dependências e construção do projeto.
-  **PostgreSQL** — banco de dados relacional.
-  **JDBC** — conexão e comunicação com o banco de dados.
-  **Jakarta Servlets** — processamento de requisições HTTP.
-  **JSP** — páginas web.

##  Estrutura do projeto

```text
Olius_Backend_Primeiro/
├── docs/                 # Site publico publicado no GitHub Pages
│   ├── index.html
│   ├── como_funciona.html
│   ├── planos.html
│   ├── contato.html
│   ├── css/
│   ├── assets/
│   └── images/
├── src/main/             # Projeto Java, Servlets, DAOs, Models e JSPs
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Publicacao da landing page

A landing page esta na pasta `docs` deste mesmo repositorio. Ela e uma copia
estatica das paginas publicas; o sistema Java continua na pasta `src`.

No GitHub, abra **Settings > Pages** e configure:

- Source: **Deploy from a branch**.
- Branch: **main**.
- Folder: **/docs**.
- Clique em **Save**.

O GitHub mostrara o endereco do site nessa tela apos a publicacao.
Nao selecionar `/(root)`: neste repositorio, o site esta dentro de `docs`.

Os botoes Agendar coleta apontam para https://olius-frontend-segundo.onrender.com.
O site publico nao usa banco nem exige login. O acesso ADM nao e publicado no
GitHub Pages; o sistema Java ainda nao possui endereco publico.
As paginas JSP continuam no projeto e exigem Tomcat para executar.

Para atualizar o site, altere os HTMLs, CSS e imagens dentro de `docs` e envie
as alteracoes para a branch publicada. Alteracoes somente em `src/main/webapp`
nao atualizam automaticamente a copia estatica de `docs`.

## Configuracao local

Nao versionar `.env`, `cpf.env`, `CPF_CHAVE`, senhas, backups ou arquivos `target`.
As credenciais do sistema Java ficam no ambiente do Tomcat, fora da pasta `docs`.
