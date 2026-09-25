package Organizacao.Model;

// Representa a entidade B2c do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class B2cModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_usuario;
    private String cpf;
    private String telefone;

    // Construtor completo: cria o objeto B2c já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public B2cModel(String cpf, String telefone) {
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // Construtor completo: cria o objeto B2c já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public B2cModel(int id_usuario, String cpf, String telefone) {
        this.id_usuario = id_usuario;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}