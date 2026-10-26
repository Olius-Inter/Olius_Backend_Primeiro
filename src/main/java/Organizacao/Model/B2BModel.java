package Organizacao.Model;

import Organizacao.Model.Enum.Plano;

public class B2BModel {

    // Atributos
    private int idB2B;
    private String cnpj;
    private Plano plano;
    private int idUsuario;

    // Construtores
    public B2BModel(String cnpj, Plano plano, int idUsuario) {
        this.cnpj = cnpj;
        this.plano = plano;
        this.idUsuario = idUsuario;
    }

    public B2BModel(int idB2B, String cnpj, Plano plano, int idUsuario) {
        this.idB2B = idB2B;
        this.cnpj = cnpj;
        this.plano = plano;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdB2B() {
        return idB2B;
    }

    public void setIdB2B(int idB2B) {
        this.idB2B = idB2B;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}