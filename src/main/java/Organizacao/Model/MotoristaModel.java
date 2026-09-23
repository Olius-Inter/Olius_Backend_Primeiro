package Organizacao.Model;

public class MotoristaModel {

    private int id_motorista;
    private String nome;
    private String cpf;
    private String telefone;
    private String cnh;
    private String empresa;
    private String status;

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