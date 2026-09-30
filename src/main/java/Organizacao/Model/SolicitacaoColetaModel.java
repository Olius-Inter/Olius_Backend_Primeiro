package Organizacao.Model;


// representa os dados da entidade solicitacao de coleta
public class SolicitacaoColetaModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_solicitacao;
    private double litros_estimados;
    private String dt_solicitacao;
    private String status;
    private int id_b2b;
    private int id_pev;

    // inicializa os dados de solicitacao de coleta com identificador
    public SolicitacaoColetaModel(double litros_estimados, String dt_solicitacao, String status, int id_b2b, int id_pev) {
        this.litros_estimados = litros_estimados;
        this.dt_solicitacao = dt_solicitacao;
        this.status = status;
        this.id_b2b = id_b2b;
        this.id_pev = id_pev;
    }

    // retorna o valor de id solicitacao
    public int getId_solicitacao() {
        return id_solicitacao;
    }

    // atualiza o valor de id solicitacao
    public void setId_solicitacao(int id_solicitacao) {
        this.id_solicitacao = id_solicitacao;
    }

    // retorna o valor de litros estimados
    public double getLitros_estimados() {
        return litros_estimados;
    }

    // retorna o valor de dt solicitacao
    public String getDt_solicitacao() {
        return dt_solicitacao;
    }

    // retorna o valor de status
    public String getStatus() {
        return status;
    }

    // retorna o valor de id b b
    public int getId_b2b() {
        return id_b2b;
    }

    // retorna o valor de id pev
    public int getId_pev() {
        return id_pev;
    }

    // atualiza o valor de litros estimados
    public void setLitros_estimados(double litros_estimados) {
        this.litros_estimados = litros_estimados;
    }

    // atualiza o valor de dt solicitacao
    public void setDt_solicitacao(String dt_solicitacao) {
        this.dt_solicitacao = dt_solicitacao;
    }

    // atualiza o valor de status
    public void setStatus(String status) {
        this.status = status;
    }

    // atualiza o valor de id b b
    public void setId_b2b(int id_b2b) {
        this.id_b2b = id_b2b;
    }

    // atualiza o valor de id pev
    public void setId_pev(int id_pev) {
        this.id_pev = id_pev;
    }
}