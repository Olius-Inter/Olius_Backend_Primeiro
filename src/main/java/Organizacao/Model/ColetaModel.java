package Organizacao.Model;

import java.time.LocalDate;

public class ColetaModel {

    // Atributos
    private int idColeta;
    private LocalDate dtColeta;
    private double volumeColetado;
    private int avaliacaoColeta;
    private int idSolicitacao;
    private int idMotorista;
    private int idB2B;

    // Construtores
    public ColetaModel(LocalDate dtColeta, double volumeColetado, int avaliacaoColeta, int idSolicitacao, int idMotorista, int idB2B) {
        this.dtColeta = dtColeta;
        this.volumeColetado = volumeColetado;
        this.avaliacaoColeta = avaliacaoColeta;
        this.idSolicitacao = idSolicitacao;
        this.idMotorista = idMotorista;
        this.idB2B = idB2B;
    }

    public ColetaModel(int idColeta, LocalDate dtColeta, double volumeColetado, int avaliacaoColeta, int idSolicitacao, int idMotorista, int idB2B) {
        this.idColeta = idColeta;
        this.dtColeta = dtColeta;
        this.volumeColetado = volumeColetado;
        this.avaliacaoColeta = avaliacaoColeta;
        this.idSolicitacao = idSolicitacao;
        this.idMotorista = idMotorista;
        this.idB2B = idB2B;
    }

    // Getters e Setters
    public int getIdColeta() {
        return idColeta;
    }

    public void setIdColeta(int idColeta) {
        this.idColeta = idColeta;
    }

    public LocalDate getDtColeta() {
        return dtColeta;
    }

    public void setDtColeta(LocalDate dtColeta) {
        this.dtColeta = dtColeta;
    }

    public double getVolumeColetado() {
        return volumeColetado;
    }

    public void setVolumeColetado(double volumeColetado) {
        this.volumeColetado = volumeColetado;
    }

    public int getAvaliacaoColeta() {
        return avaliacaoColeta;
    }

    public void setAvaliacaoColeta(int avaliacaoColeta) {
        this.avaliacaoColeta = avaliacaoColeta;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public int getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(int idMotorista) {
        this.idMotorista = idMotorista;
    }

    public int getIdB2B() {
        return idB2B;
    }

    public void setIdB2B(int idB2B) {
        this.idB2B = idB2B;
    }
}