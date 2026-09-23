package Organizacao.Model;

public class B2cModel {

    private int id_usuario;
    private String cpf;
    private String telefone;

    public B2cModel(String cpf, String telefone) {
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public B2cModel(int id_usuario, String cpf, String telefone) {
        this.id_usuario = id_usuario;
        this.cpf = cpf;
        this.telefone = telefone;
    }

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