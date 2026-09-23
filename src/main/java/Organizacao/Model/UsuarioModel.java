package Organizacao.Model;

import java.sql.Date;

public class UsuarioModel {

    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private Date primeiroRegistro;
    private String tipoUsuario;
    private String telefone;

    public UsuarioModel() {
    }

    public UsuarioModel(int id_usuario, String nome, String email, String senha,
                       Date primeiroRegistro, String tipoUsuario, String telefone) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.primeiroRegistro = primeiroRegistro;
        this.tipoUsuario = tipoUsuario;
        this.telefone = telefone;
    }

    public UsuarioModel(int id_usuario, String email, String senha, Date primeiroRegistro,
                       String tipoUsuario, String telefone, String nome) {
        this(id_usuario, nome, email, senha, primeiroRegistro, tipoUsuario, telefone);
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getPrimeiroRegistro() {
        return primeiroRegistro;
    }

    public void setPrimeiroRegistro(Date primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
