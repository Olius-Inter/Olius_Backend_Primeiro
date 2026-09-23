package Organizacao.Model;

import java.time.LocalDate;

public class PontuacaoModel {

    private int id_pontuacao;
    private int id_usuario_b2c;
    private int id_entrega;
    private int pontos;
    private String tp_movimentacao;
    private String descricao;
    private LocalDate dt_movimentacao;

    public PontuacaoModel(int id_usuario_b2c, int id_entrega,
                          int pontos, String tp_movimentacao,
                          String descricao, LocalDate dt_movimentacao) {

        this.id_usuario_b2c = id_usuario_b2c;
        this.id_entrega = id_entrega;
        this.pontos = pontos;
        this.tp_movimentacao = tp_movimentacao;
        this.descricao = descricao;
        this.dt_movimentacao = dt_movimentacao;
    }

    public int getId_pontuacao() {
        return id_pontuacao;
    }

    public void setId_pontuacao(int id_pontuacao) {
        this.id_pontuacao = id_pontuacao;
    }

    public int getId_usuario_b2c() {
        return id_usuario_b2c;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public int getPontos() {
        return pontos;
    }

    public String getTp_movimentacao() {
        return tp_movimentacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDt_movimentacao() {
        return dt_movimentacao;
    }

    public void setId_usuario_b2c(int id_usuario_b2c) {
        this.id_usuario_b2c = id_usuario_b2c;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void setTp_movimentacao(String tp_movimentacao) {
        this.tp_movimentacao = tp_movimentacao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDt_movimentacao(LocalDate dt_movimentacao) {
        this.dt_movimentacao = dt_movimentacao;
    }
}