package Organizacao.Model;

import Organizacao.Model.Enum.StatusPev;

import java.time.LocalDate;

public class PevModel {

    // Atributos
    private int idPev;
    private StatusPev status;
    private LocalDate dtAprovacao;
    private int idUsuario;

    // Construtores
    public PevModel(StatusPev status, LocalDate dtAprovacao, int idUsuario) {
        this.status = status;
        this.dtAprovacao = dtAprovacao;
        this.idUsuario = idUsuario;
    }

    public PevModel(int idPev, StatusPev status, LocalDate dtAprovacao, int idUsuario) {
        this.idPev = idPev;
        this.status = status;
        this.dtAprovacao = dtAprovacao;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdPev() {
        return idPev;
    }

    public void setIdPev(int idPev) {
        this.idPev = idPev;
    }

    public StatusPev getStatus() {
        return status;
    }

    public void setStatus(StatusPev status) {
        this.status = status;
    }

    public LocalDate getDtAprovacao() {
        return dtAprovacao;
    }

    public void setDtAprovacao(LocalDate dtAprovacao) {
        this.dtAprovacao = dtAprovacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}