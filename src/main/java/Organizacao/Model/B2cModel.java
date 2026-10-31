package Organizacao.Model;

// Cliente pessoa física: dados específicos do usuário do tipo B2C
public class B2cModel {

    private int idB2c;
    private int idUsuario;
    private String cpf;

    public B2cModel(int idUsuario, String cpf) {
        this.idUsuario = idUsuario;
        this.cpf = cpf;
    }

    public B2cModel(int idB2c, int idUsuario, String cpf) {
        this(idUsuario, cpf);
        this.idB2c = idB2c;
    }

    public int getIdB2c() { return idB2c; }
    public void setIdB2c(int idB2c) { this.idB2c = idB2c; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
