package Organizacao.Model;

// Representa a entidade Evento do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class EventoModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_evento;
    private String nome;
    private String descricao;
    private String dt_finalizacao;
    private String dt_inicio;
    private int id_b2b;

    // Construtor completo: cria o objeto Evento já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public EventoModel(String nome, String descricao, String dt_finalizacao, String dt_inicio, int id_b2b) {
        this.nome = nome;
        this.descricao = descricao;
        this.dt_finalizacao = dt_finalizacao;
        this.dt_inicio = dt_inicio;
        this.id_b2b = id_b2b;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDt_finalizacao() {
        return dt_finalizacao;
    }

    public String getDt_inicio() {
        return dt_inicio;
    }

    public int getId_b2b() {
        return id_b2b;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDt_finalizacao(String dt_finalizacao) {
        this.dt_finalizacao = dt_finalizacao;
    }

    public void setDt_inicio(String dt_inicio) {
        this.dt_inicio = dt_inicio;
    }

    public void setId_b2b(int id_b2b) {
        this.id_b2b = id_b2b;
    }
}