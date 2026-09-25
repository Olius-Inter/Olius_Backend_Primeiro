package Organizacao.Model;

import java.sql.Date;

// Representa a entidade Usuario do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class UsuarioModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_usuario;
    private String nome;
    private String email;
    private String senha;
    private Date primeiroRegistro;
    private String tipoUsuario;
    private String telefone;

    // Construtor padrão (sem argumentos).
    // Cria o objeto vazio, para ser preenchido depois via métodos set().
    public UsuarioModel() {
    }

    // Construtor completo: cria o objeto Usuario já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
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

    // Construtor alternativo, mantido por conveniência: reorganiza a
    // ordem dos parâmetros recebidos e delega a criação do objeto para
    // o construtor completo.
    public UsuarioModel(int id_usuario, String email, String senha, Date primeiroRegistro,
                       String tipoUsuario, String telefone, String nome) {
        this(id_usuario, nome, email, senha, primeiroRegistro, tipoUsuario, telefone);
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
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
