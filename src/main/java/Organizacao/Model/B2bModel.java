package Organizacao.Model;

// Representa a entidade B2b do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class B2bModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_usuario;
    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String telefone;
    private int id_endereco;

    // Construtor completo: cria o objeto B2b já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public B2bModel(String cnpj, String razao_social,
                    String nome_fantasia, String telefone,
                    int id_endereco) {

        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // Construtor completo: cria o objeto B2b já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public B2bModel(int id_usuario, String cnpj, String razao_social, String nome_fantasia, String telefone, int id_endereco) {
        this.id_usuario = id_usuario;
        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_usuario() {
        return id_usuario;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }
}