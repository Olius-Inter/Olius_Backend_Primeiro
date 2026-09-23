package Organizacao.Model;

import java.sql.Date;

public class UsuarioModel {

    private int id_usuario;
    private String email;
    private String senha;
    private Date primeiroRegistro;
    private String tipoUsuario;
    private String telefone;
    private String nome;

    public UsuarioModel(int idUsuario, String nome, String email, String senha, Date primeiroRegistro, String tipoUsuario, String telefone) {}

    public UsuarioModel(int id_usuario, String email, String senha, Date primeiroRegistro, String tipoUsuario, String telefone, String nome) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.senha = senha;
        this.primeiroRegistro = primeiroRegistro;
        this.tipoUsuario = tipoUsuario;
        this.telefone = telefone;
        this.nome = nome;
    }

    public int getId_usuario() { return id_usuario; }
    public void setIdUsuario(int idUsuario) { this.id_usuario = id_usuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Date getPrimeiroRegistro() {
        return primeiroRegistro; }
    public void setPrimeiroRegistro(Date primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}