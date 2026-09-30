package Organizacao.Model;


// representa os dados da entidade usuario b dois c
public class B2cModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_usuario;
    private String cpf;
    private String telefone;

    // inicializa os dados de usuario b dois c
    public B2cModel(String cpf, String telefone) {
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // inicializa os dados de usuario b dois c com identificador
    public B2cModel(int id_usuario, String cpf, String telefone) {
        this.id_usuario = id_usuario;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // retorna o valor de id usuario
    public int getId_usuario() {
        return id_usuario;
    }

    // atualiza o valor de id usuario
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    // retorna o valor de cpf
    public String getCpf() {
        return cpf;
    }

    // retorna o valor de telefone
    public String getTelefone() {
        return telefone;
    }

    // atualiza o valor de cpf
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // atualiza o valor de telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}