package Organizacao.Model;

import Organizacao.Model.Enum.EmAtivo;

public class AdmModel {

    // Atributos
    private int idAdm;
    private String nome;
    private String email;
    private EmAtivo emAtivo;

    // Construtores
    public AdmModel(String nome, String email, EmAtivo emAtivo) {
        this.nome = nome;
        this.email = email;
        this.emAtivo = emAtivo != null ? emAtivo : EmAtivo.Ativo;
    }

    public AdmModel(int idAdm, String nome, String email, EmAtivo emAtivo) {
        this.idAdm = idAdm;
        this.nome = nome;
        this.email = email;
        this.emAtivo = emAtivo != null ? emAtivo : EmAtivo.Ativo;
    }

    // Getters e Setters
    public int getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(int idAdm) {
        this.idAdm = idAdm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmAtivo getEmAtivo() {
        return emAtivo;
    }

    public void setEmAtivo(EmAtivo emAtivo) {
        this.emAtivo = emAtivo;
    }
}