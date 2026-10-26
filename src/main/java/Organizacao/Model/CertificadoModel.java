package Organizacao.Model;

import java.time.LocalDate;

public class CertificadoModel {

    // Atributos
    private int idCertificado;
    private double volumeTotal;
    private LocalDate dtEmissao;
    private String pdf;
    private int idB2B;
    private int idColeta;

    // Construtores
    public CertificadoModel(double volumeTotal, LocalDate dtEmissao, String pdf, int idB2B, int idColeta) {
        this.volumeTotal = volumeTotal;
        this.dtEmissao = dtEmissao;
        this.pdf = pdf;
        this.idB2B = idB2B;
        this.idColeta = idColeta;
    }

    public CertificadoModel(int idCertificado, double volumeTotal, LocalDate dtEmissao, String pdf, int idB2B, int idColeta) {
        this.idCertificado = idCertificado;
        this.volumeTotal = volumeTotal;
        this.dtEmissao = dtEmissao;
        this.pdf = pdf;
        this.idB2B = idB2B;
        this.idColeta = idColeta;
    }

    // Getters e Setters
    public int getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(int idCertificado) {
        this.idCertificado = idCertificado;
    }

    public double getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(double volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public LocalDate getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(LocalDate dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public int getIdB2B() {
        return idB2B;
    }

    public void setIdB2B(int idB2B) {
        this.idB2B = idB2B;
    }

    public int getIdColeta() {
        return idColeta;
    }

    public void setIdColeta(int idColeta) {
        this.idColeta = idColeta;
    }
}