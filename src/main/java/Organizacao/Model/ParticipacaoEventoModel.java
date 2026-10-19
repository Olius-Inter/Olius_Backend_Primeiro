package Organizacao.Model;


// representa os dados da entidade participacao em evento
public class ParticipacaoEventoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_participacao;
    private String status;
    private int id_b2c;
    private int id_evento;

    // inicializa os dados de participacao em evento com identificador
    public ParticipacaoEventoModel(String status, int id_b2c, int id_evento) {
        this.status = status;
        this.id_b2c = id_b2c;
        this.id_evento = id_evento;
    }

    // retorna o valor de id participacao
    public int getId_participacao() {
        return id_participacao;
    }

    // atualiza o valor de id participacao
    public void setId_participacao(int id_participacao) {
        this.id_participacao = id_participacao;
    }

    // retorna o valor de status
    public String getStatus() {
        return status;
    }

    // retorna o valor de id b c
    public int getId_b2c() {
        return id_b2c;
    }

    // retorna o valor de id evento
    public int getId_evento() {
        return id_evento;
    }

    // atualiza o valor de status
    public void setStatus(String status) {
        this.status = status;
    }

    // atualiza o valor de id b c
    public void setId_b2c(int id_b2c) {
        this.id_b2c = id_b2c;
    }

    // atualiza o valor de id evento
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }
}