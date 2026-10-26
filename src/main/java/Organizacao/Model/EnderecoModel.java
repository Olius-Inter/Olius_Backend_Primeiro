package Organizacao.Model;

import Organizacao.Model.Enum.Estados;

public class EnderecoModel {

    // Atributos
    private int idEndereco;
    private String rua;
    private String cidade;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private Estados estado;

    // Construtores
    public EnderecoModel(String rua, String cidade, String bairro, String cep, String numero, String complemento, Estados estado) {
        this.rua = rua;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.estado = estado;
    }

    public EnderecoModel(int idEndereco, String rua, String cidade, String bairro, String cep, String numero, String complemento, Estados estado) {
        this.idEndereco = idEndereco;
        this.rua = rua;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.estado = estado;
    }

    // Getters e Setters
    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
}