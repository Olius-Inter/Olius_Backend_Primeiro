package Organizacao.Model;

import java.time.LocalDate;

// Representa a entidade Certificado do sistema.

public class CertificadoModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_certificado;
    private int id_usuario_b2b;
    private String codigo;
    private String nivel;
    private double volume_total;
    private LocalDate dt_emissao;
    private String arquivo_pdf;

    // Construtor completo: cria o objeto Certificado já com todos os dados informados
    public CertificadoModel(int id_usuario_b2b,
                            String codigo,
                            String nivel,
                            double volume_total,
                            LocalDate dt_emissao,
                            String arquivo_pdf) {

        this.id_usuario_b2b = id_usuario_b2b;
        this.codigo = codigo;
        this.nivel = nivel;
        this.volume_total = volume_total;
        this.dt_emissao = dt_emissao;
        this.arquivo_pdf = arquivo_pdf;
    }

    // Getters e setters
    public int getId_certificado() {
        return id_certificado;
    }

    public void setId_certificado(int id_certificado) {
        this.id_certificado = id_certificado;
    }

    public int getId_usuario_b2b() {
        return id_usuario_b2b;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNivel() {
        return nivel;
    }

    public double getVolume_total() {
        return volume_total;
    }

    public LocalDate getDt_emissao() {
        return dt_emissao;
    }

    public String getArquivo_pdf() {
        return arquivo_pdf;
    }

    public void setId_usuario_b2b(int id_usuario_b2b) {
        this.id_usuario_b2b = id_usuario_b2b;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setVolume_total(double volume_total) {
        this.volume_total = volume_total;
    }

    public void setDt_emissao(LocalDate dt_emissao) {
        this.dt_emissao = dt_emissao;
    }

    public void setArquivo_pdf(String arquivo_pdf) {
        this.arquivo_pdf = arquivo_pdf;
    }
}