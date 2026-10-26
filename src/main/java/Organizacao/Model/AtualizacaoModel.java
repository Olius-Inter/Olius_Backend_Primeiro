package Organizacao.Model;

public class AtualizacaoModel {

    // Atributos
    private int idAtualizacao;
    private String motivo;
    private int autorizacao;
    private String mudanca;

    // Construtores
    public AtualizacaoModel(String motivo, int autorizacao, String mudanca) {
        this.motivo = motivo;
        this.autorizacao = autorizacao;
        this.mudanca = mudanca;
    }

    public AtualizacaoModel(int idAtualizacao, String motivo, int autorizacao, String mudanca) {
        this.idAtualizacao = idAtualizacao;
        this.motivo = motivo;
        this.autorizacao = autorizacao;
        this.mudanca = mudanca;
    }

    // Getters e Setters

    public int getIdAtualizacao() {
        return idAtualizacao;
    }

    public void setIdAtualizacao(int idAtualizacao) {
        this.idAtualizacao = idAtualizacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(int autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getMudanca() {
        return mudanca;
    }

    public void setMudanca(String mudanca) {
        this.mudanca = mudanca;
    }
}