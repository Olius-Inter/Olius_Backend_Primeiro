package Organizacao.Model;

import java.time.LocalDate;


// representa os dados da entidade certificado empresarial
public class CertificadoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_certificado;
    private int id_usuario_b2b;
    private String codigo;
    private String nivel;
    private double volume_total;
    private LocalDate dt_emissao;
    private String arquivo_pdf;

    // inicializa os dados de certificado empresarial com identificador
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

    // retorna o valor de id certificado
    public int getId_certificado() {
        return id_certificado;
    }

    // atualiza o valor de id certificado
    public void setId_certificado(int id_certificado) {
        this.id_certificado = id_certificado;
    }

    // retorna o valor de id usuario b b
    public int getId_usuario_b2b() {
        return id_usuario_b2b;
    }

    // retorna o valor de codigo
    public String getCodigo() {
        return codigo;
    }

    // retorna o valor de nivel
    public String getNivel() {
        return nivel;
    }

    // retorna o valor de volume total
    public double getVolume_total() {
        return volume_total;
    }

    // retorna o valor de dt emissao
    public LocalDate getDt_emissao() {
        return dt_emissao;
    }

    // retorna o valor de arquivo pdf
    public String getArquivo_pdf() {
        return arquivo_pdf;
    }

    // atualiza o valor de id usuario b b
    public void setId_usuario_b2b(int id_usuario_b2b) {
        this.id_usuario_b2b = id_usuario_b2b;
    }

    // atualiza o valor de codigo
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // atualiza o valor de nivel
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    // atualiza o valor de volume total
    public void setVolume_total(double volume_total) {
        this.volume_total = volume_total;
    }

    // atualiza o valor de dt emissao
    public void setDt_emissao(LocalDate dt_emissao) {
        this.dt_emissao = dt_emissao;
    }

    // atualiza o valor de arquivo pdf
    public void setArquivo_pdf(String arquivo_pdf) {
        this.arquivo_pdf = arquivo_pdf;
    }
}