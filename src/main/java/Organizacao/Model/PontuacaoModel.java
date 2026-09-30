package Organizacao.Model;

import java.time.LocalDate;


// representa os dados da entidade pontuacao de usuario
public class PontuacaoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_pontuacao;
    private int id_usuario_b2c;
    private int id_entrega;
    private int pontos;
    private String tp_movimentacao;
    private String descricao;
    private LocalDate dt_movimentacao;

    // inicializa os dados de pontuacao de usuario com identificador
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

    // retorna o valor de id pontuacao
    public int getId_pontuacao() {
        return id_pontuacao;
    }

    // atualiza o valor de id pontuacao
    public void setId_pontuacao(int id_pontuacao) {
        this.id_pontuacao = id_pontuacao;
    }

    // retorna o valor de id usuario b c
    public int getId_usuario_b2c() {
        return id_usuario_b2c;
    }

    // retorna o valor de id entrega
    public int getId_entrega() {
        return id_entrega;
    }

    // retorna o valor de pontos
    public int getPontos() {
        return pontos;
    }

    // retorna o valor de tp movimentacao
    public String getTp_movimentacao() {
        return tp_movimentacao;
    }

    // retorna o valor de descricao
    public String getDescricao() {
        return descricao;
    }

    // retorna o valor de dt movimentacao
    public LocalDate getDt_movimentacao() {
        return dt_movimentacao;
    }

    // atualiza o valor de id usuario b c
    public void setId_usuario_b2c(int id_usuario_b2c) {
        this.id_usuario_b2c = id_usuario_b2c;
    }

    // atualiza o valor de id entrega
    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    // atualiza o valor de pontos
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    // atualiza o valor de tp movimentacao
    public void setTp_movimentacao(String tp_movimentacao) {
        this.tp_movimentacao = tp_movimentacao;
    }

    // atualiza o valor de descricao
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // atualiza o valor de dt movimentacao
    public void setDt_movimentacao(LocalDate dt_movimentacao) {
        this.dt_movimentacao = dt_movimentacao;
    }
}