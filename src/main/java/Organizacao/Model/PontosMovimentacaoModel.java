package Organizacao.Model;

public class PontosMovimentacaoModel {

    private int id_movimentacao;
    private int pontos_ganhos;
    private String tipo_movimentacao;
    private String dt_movimentacao;
    private int id_entrega; // FK de Entrega_PEV
    private int id_carteira;
    private int id_participacao;

    public PontosMovimentacaoModel(int pontos_ganhos, String tipo_movimentacao, String dt_movimentacao, int id_entrega, int id_carteira, int id_participacao) {
        this.pontos_ganhos = pontos_ganhos;
        this.tipo_movimentacao = tipo_movimentacao;
        this.dt_movimentacao = dt_movimentacao;
        this.id_entrega = id_entrega;
        this.id_carteira = id_carteira;
        this.id_participacao = id_participacao;
    }

    public int getId_movimentacao() {
        return id_movimentacao;
    }

    public void setId_movimentacao(int id_movimentacao) {
        this.id_movimentacao = id_movimentacao;
    }

    public int getPontos_ganhos() {
        return pontos_ganhos;
    }

    public String getTipo_movimentacao() {
        return tipo_movimentacao;
    }

    public String getDt_movimentacao() {
        return dt_movimentacao;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public int getId_carteira() {
        return id_carteira;
    }

    public int getId_participacao() {
        return id_participacao;
    }

    public void setPontos_ganhos(int pontos_ganhos) {
        this.pontos_ganhos = pontos_ganhos;
    }

    public void setTipo_movimentacao(String tipo_movimentacao) {
        this.tipo_movimentacao = tipo_movimentacao;
    }

    public void setDt_movimentacao(String dt_movimentacao) {
        this.dt_movimentacao = dt_movimentacao;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public void setId_carteira(int id_carteira) {
        this.id_carteira = id_carteira;
    }

    public void setId_participacao(int id_participacao) {
        this.id_participacao = id_participacao;
    }
}