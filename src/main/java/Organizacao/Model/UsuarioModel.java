package Organizacao.Model;

import Organizacao.Model.Enum.TipoUsuario;

import java.time.LocalDate;

public class UsuarioModel {

    // Atributos
    private int idUsuario;
    private String email;
    private String telefone;
    private String nome;
    private LocalDate primeiroRegistro;
    private String senha;
    private TipoUsuario tipoUsuario;
    private int idEndereco;

    // Construtores
    public UsuarioModel(String email, String telefone, String nome, LocalDate primeiroRegistro, String senha, TipoUsuario tipoUsuario, int idEndereco) {
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.primeiroRegistro = primeiroRegistro != null ? primeiroRegistro : LocalDate.now();
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.idEndereco = idEndereco;
    }

    public UsuarioModel(int idUsuario, String email, String telefone, String nome, LocalDate primeiroRegistro, String senha, TipoUsuario tipoUsuario, int idEndereco) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.telefone = telefone;
        this.nome = nome;
        this.primeiroRegistro = primeiroRegistro != null ? primeiroRegistro : LocalDate.now();
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.idEndereco = idEndereco;
    }

    // Getters e Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getPrimeiroRegistro() {
        return primeiroRegistro;
    }

    public void setPrimeiroRegistro(LocalDate primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }
}