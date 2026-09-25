package Organizacao.Model;

// Representa a entidade Motorista do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class MotoristaModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_motorista;
    private String nome;
    private String cpf;
    private String telefone;
    private String cnh;
    private String empresa;
    private String status;

    // Construtor completo: cria o objeto Motorista já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public MotoristaModel(String nome, String cpf,
                          String telefone, String cnh,
                          String empresa, String status) {

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cnh = cnh;
        this.empresa = empresa;
        this.status = status;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_motorista() {
        return id_motorista;
    }

    public void setId_motorista(int id_motorista) {
        this.id_motorista = id_motorista;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getStatus() {
        return status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}