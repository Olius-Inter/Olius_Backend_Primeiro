package Organizacao.Model;

import Organizacao.Model.Enum.StatusSolicitacao;

import java.time.LocalDate;

public class SolicitacaoColetaModel {

    // Atributos
    private int idSolicitacao;
    private StatusSolicitacao status;
    private double volumeEstimado;
    private LocalDate dtSolicitacao;
    private int idB2B;

    // Construtores
    public SolicitacaoColetaModel(StatusSolicitacao status, double volumeEstimado, LocalDate dtSolicitacao, int idB2B) {
        this.status = status;
        this.volumeEstimado = volumeEstimado;
        this.dtSolicitacao = dtSolicitacao;
        this.idB2B = idB2B;
    }

    public SolicitacaoColetaModel(int idSolicitacao, StatusSolicitacao status, double volumeEstimado, LocalDate dtSolicitacao, int idB2B) {
        this.idSolicitacao = idSolicitacao;
        this.status = status;
        this.volumeEstimado = volumeEstimado;
        this.dtSolicitacao = dtSolicitacao;
        this.idB2B = idB2B;
    }

    // Getters e Setters
    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public double getVolumeEstimado() {
        return volumeEstimado;
    }

    public void setVolumeEstimado(double volumeEstimado) {
        this.volumeEstimado = volumeEstimado;
    }

    public LocalDate getDtSolicitacao() {
        return dtSolicitacao;
    }

    public void setDtSolicitacao(LocalDate dtSolicitacao) {
        this.dtSolicitacao = dtSolicitacao;
    }

    public int getIdB2B() {
        return idB2B;
    }

    public void setIdB2B(int idB2B) {
        this.idB2B = idB2B;
    }
}