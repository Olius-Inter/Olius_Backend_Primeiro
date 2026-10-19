package Organizacao.Model;


// representa os dados da entidade motorista
public class MotoristaModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_motorista;
    private String nome;
    private String cpf;
    private String telefone;
    private String cnh;
    private String empresa;
    private String status;

    // inicializa os dados de motorista
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

    // retorna o valor de id motorista
    public int getId_motorista() {
        return id_motorista;
    }

    // atualiza o valor de id motorista
    public void setId_motorista(int id_motorista) {
        this.id_motorista = id_motorista;
    }

    // retorna o valor de nome
    public String getNome() {
        return nome;
    }

    // retorna o valor de cpf
    public String getCpf() {
        return cpf;
    }

    // retorna o valor de telefone
    public String getTelefone() {
        return telefone;
    }

    // retorna o valor de cnh
    public String getCnh() {
        return cnh;
    }

    // retorna o valor de empresa
    public String getEmpresa() {
        return empresa;
    }

    // retorna o valor de status
    public String getStatus() {
        return status;
    }

    // atualiza o valor de nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // atualiza o valor de cpf
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // atualiza o valor de telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // atualiza o valor de cnh
    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    // atualiza o valor de empresa
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    // atualiza o valor de status
    public void setStatus(String status) {
        this.status = status;
    }
}