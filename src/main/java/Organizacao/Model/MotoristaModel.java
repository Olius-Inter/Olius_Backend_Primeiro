package Organizacao.Model;

import Organizacao.Model.Enum.StatusMotorista;

public class MotoristaModel {

    // Atributos
    private int idMotorista;
    private StatusMotorista status;
    private String empresaResp;
    private String telefone;
    private String nome;
    private String licenca;

    // Construtores
    public MotoristaModel(StatusMotorista status, String empresaResp, String telefone, String nome, String licenca) {
        this.status = status;
        this.empresaResp = empresaResp;
        this.telefone = telefone;
        this.nome = nome;
        this.licenca = licenca;
    }

    public MotoristaModel(int idMotorista, StatusMotorista status, String empresaResp, String telefone, String nome, String licenca) {
        this.idMotorista = idMotorista;
        this.status = status;
        this.empresaResp = empresaResp;
        this.telefone = telefone;
        this.nome = nome;
        this.licenca = licenca;
    }

    // Getters e Setters
    public int getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(int idMotorista) {
        this.idMotorista = idMotorista;
    }

    public StatusMotorista getStatus() {
        return status;
    }

    public void setStatus(StatusMotorista status) {
        this.status = status;
    }

    public String getEmpresaResp() {
        return empresaResp;
    }

    public void setEmpresaResp(String empresaResp) {
        this.empresaResp = empresaResp;
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

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }
}