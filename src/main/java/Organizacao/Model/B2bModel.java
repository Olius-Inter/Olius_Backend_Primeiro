package Organizacao.Model;

// Representa a entidade B2b do sistema.

public class B2bModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_usuario;
    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String telefone;
    private int id_endereco;

    // Construtor completo: cria o objeto B2b já com todos os

    public B2bModel(String cnpj, String razao_social,
                    String nome_fantasia, String telefone,
                    int id_endereco) {

        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // Construtor completo: cria o objeto B2b já com todos os dados informado.
    public B2bModel(int id_usuario, String cnpj, String razao_social, String nome_fantasia, String telefone, int id_endereco) {
        this.id_usuario = id_usuario;
        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // Getters e setters
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