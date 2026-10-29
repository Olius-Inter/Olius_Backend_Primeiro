Sistema desenvolvido como parte de um projeto interdisciplinar do 1º ano, utilizando Java e PostgreSQL para estruturar o backend e gerenciar os dados da aplicação.

Sobre o projeto

O OLIUS possui uma estrutura organizada para separar as responsabilidades do sistema, facilitando o desenvolvimento, a manutenção e o acesso aos dados.

O backend contém classes para conexão com o banco de dados, modelos de entidades, operações de acesso aos dados (DAO) e Servlets para receber e processar requisições HTTP.

##Tecnologias utilizadas

-  **Java** — linguagem de programação.
-  **Maven** — gerenciamento de dependências e construção do projeto.
-  **PostgreSQL** — banco de dados relacional.
-  **JDBC** — conexão e comunicação com o banco de dados.
-  **Jakarta Servlets** — processamento de requisições HTTP.
-  **JSP** — páginas web.

##  Estrutura do projeto

```text
BACK_END_OLIUS/
├── src/
│   └── main/
│       ├── java/
│       │   └── Organizacao/
│       │       ├── Conexao/
│       │       ├── Dao/
│       │       ├── Model/
│       │       └── Servlet/
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       └── webapp/
│           ├── index.jsp
│           └── WEB-INF/
│               └── web
