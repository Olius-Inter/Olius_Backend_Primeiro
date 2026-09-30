package Organizacao.Model;


// representa os dados da entidade evento
public class EventoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_evento;
    private String nome;
    private String descricao;
    private String dt_finalizacao;
    private String dt_inicio;
    private int id_b2b;

    // inicializa os dados de evento com identificador
    public EventoModel(String nome, String descricao, String dt_finalizacao, String dt_inicio, int id_b2b) {
        this.nome = nome;
        this.descricao = descricao;
        this.dt_finalizacao = dt_finalizacao;
        this.dt_inicio = dt_inicio;
        this.id_b2b = id_b2b;
    }

    // retorna o valor de id evento
    public int getId_evento() {
        return id_evento;
    }

    // atualiza o valor de id evento
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    // retorna o valor de nome
    public String getNome() {
        return nome;
    }

    // retorna o valor de descricao
    public String getDescricao() {
        return descricao;
    }

    // retorna o valor de dt finalizacao
    public String getDt_finalizacao() {
        return dt_finalizacao;
    }

    // retorna o valor de dt inicio
    public String getDt_inicio() {
        return dt_inicio;
    }

    // retorna o valor de id b b
    public int getId_b2b() {
        return id_b2b;
    }

    // atualiza o valor de nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // atualiza o valor de descricao
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // atualiza o valor de dt finalizacao
    public void setDt_finalizacao(String dt_finalizacao) {
        this.dt_finalizacao = dt_finalizacao;
    }

    // atualiza o valor de dt inicio
    public void setDt_inicio(String dt_inicio) {
        this.dt_inicio = dt_inicio;
    }

    // atualiza o valor de id b b
    public void setId_b2b(int id_b2b) {
        this.id_b2b = id_b2b;
    }
}