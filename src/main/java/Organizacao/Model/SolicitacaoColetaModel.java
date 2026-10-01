package Organizacao.Model;

// Representa a entidade SolicitacaoColeta do sistema.

public class SolicitacaoColetaModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_solicitacao;
    private double litros_estimados;
    private String dt_solicitacao;
    private String status;
    private int id_b2b;
    private int id_pev;

    // Construtor: cria o objeto SolicitacaoColeta já com todos os dados informados
    public SolicitacaoColetaModel(double litros_estimados, String dt_solicitacao, String status, int id_b2b, int id_pev) {
        this.litros_estimados = litros_estimados;
        this.dt_solicitacao = dt_solicitacao;
        this.status = status;
        this.id_b2b = id_b2b;
        this.id_pev = id_pev;
    }

    // Getters e setters
    public int getId_solicitacao() {
        return id_solicitacao;
    }

    public void setId_solicitacao(int id_solicitacao) {
        this.id_solicitacao = id_solicitacao;
    }

    public double getLitros_estimados() {
        return litros_estimados;
    }

    public String getDt_solicitacao() {
        return dt_solicitacao;
    }

    public String getStatus() {
        return status;
    }

    public int getId_b2b() {
        return id_b2b;
    }

    public int getId_pev() {
        return id_pev;
    }

    public void setLitros_estimados(double litros_estimados) {
        this.litros_estimados = litros_estimados;
    }

    public void setDt_solicitacao(String dt_solicitacao) {
        this.dt_solicitacao = dt_solicitacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_b2b(int id_b2b) {
        this.id_b2b = id_b2b;
    }

    public void setId_pev(int id_pev) {
        this.id_pev = id_pev;
    }
}