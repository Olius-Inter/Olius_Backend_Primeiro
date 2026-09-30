package Organizacao.Model;

import java.sql.Date;


// representa os dados da entidade usuario
public class UsuarioModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private Date primeiroRegistro;
    private String tipoUsuario;
    private String telefone;

    // inicializa os dados de usuario
    public UsuarioModel() {
    }

    // inicializa os dados de usuario com identificador
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

    // inicializa os dados de usuario com identificador
    public UsuarioModel(int id_usuario, String email, String senha, Date primeiroRegistro,
                       String tipoUsuario, String telefone, String nome) {
        this(id_usuario, nome, email, senha, primeiroRegistro, tipoUsuario, telefone);
    }

    // retorna o valor de id usuario
    public int getId_usuario() {
        return id_usuario;
    }

    // atualiza o valor de id usuario
    public void setIdUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
    }

    // retorna o valor de email
    public String getEmail() {
        return email;
    }

    // atualiza o valor de email
    public void setEmail(String email) {
        this.email = email;
    }

    // retorna o valor de senha
    public String getSenha() {
        return senha;
    }

    // atualiza o valor de senha
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // retorna o valor de primeiro registro
    public Date getPrimeiroRegistro() {
        return primeiroRegistro;
    }

    // atualiza o valor de primeiro registro
    public void setPrimeiroRegistro(Date primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro;
    }

    // retorna o valor de tipo usuario
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    // atualiza o valor de tipo usuario
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // retorna o valor de telefone
    public String getTelefone() {
        return telefone;
    }

    // atualiza o valor de telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // retorna o valor de nome
    public String getNome() {
        return nome;
    }

    // atualiza o valor de nome
    public void setNome(String nome) {
        this.nome = nome;
    }
}
