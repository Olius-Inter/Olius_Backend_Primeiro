package Organizacao.Model;


// representa os dados da entidade movimentacao de pontos
public class PontosMovimentacaoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_movimentacao;
    private int pontos_ganhos;
    private String tipo_movimentacao;
    private String dt_movimentacao;
    private int id_entrega;
    private int id_carteira;
    private int id_participacao;

    // inicializa os dados de movimentacao de pontos com identificador
    public PontosMovimentacaoModel(int pontos_ganhos, String tipo_movimentacao, String dt_movimentacao, int id_entrega, int id_carteira, int id_participacao) {
        this.pontos_ganhos = pontos_ganhos;
        this.tipo_movimentacao = tipo_movimentacao;
        this.dt_movimentacao = dt_movimentacao;
        this.id_entrega = id_entrega;
        this.id_carteira = id_carteira;
        this.id_participacao = id_participacao;
    }

    // retorna o valor de id movimentacao
    public int getId_movimentacao() {
        return id_movimentacao;
    }

    // atualiza o valor de id movimentacao
    public void setId_movimentacao(int id_movimentacao) {
        this.id_movimentacao = id_movimentacao;
    }

    // retorna o valor de pontos ganhos
    public int getPontos_ganhos() {
        return pontos_ganhos;
    }

    // retorna o valor de tipo movimentacao
    public String getTipo_movimentacao() {
        return tipo_movimentacao;
    }

    // retorna o valor de dt movimentacao
    public String getDt_movimentacao() {
        return dt_movimentacao;
    }

    // retorna o valor de id entrega
    public int getId_entrega() {
        return id_entrega;
    }

    // retorna o valor de id carteira
    public int getId_carteira() {
        return id_carteira;
    }

    // retorna o valor de id participacao
    public int getId_participacao() {
        return id_participacao;
    }

    // atualiza o valor de pontos ganhos
    public void setPontos_ganhos(int pontos_ganhos) {
        this.pontos_ganhos = pontos_ganhos;
    }

    // atualiza o valor de tipo movimentacao
    public void setTipo_movimentacao(String tipo_movimentacao) {
        this.tipo_movimentacao = tipo_movimentacao;
    }

    // atualiza o valor de dt movimentacao
    public void setDt_movimentacao(String dt_movimentacao) {
        this.dt_movimentacao = dt_movimentacao;
    }

    // atualiza o valor de id entrega
    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    // atualiza o valor de id carteira
    public void setId_carteira(int id_carteira) {
        this.id_carteira = id_carteira;
    }

    // atualiza o valor de id participacao
    public void setId_participacao(int id_participacao) {
        this.id_participacao = id_participacao;
    }
}