package Organizacao.Model;

// Representa a entidade ParticipacaoEvento do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class ParticipacaoEventoModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_participacao;
    private String status;
    private int id_b2c;
    private int id_evento;

    // Construtor completo: cria o objeto ParticipacaoEvento já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public ParticipacaoEventoModel(String status, int id_b2c, int id_evento) {
        this.status = status;
        this.id_b2c = id_b2c;
        this.id_evento = id_evento;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_participacao() {
        return id_participacao;
    }

    public void setId_participacao(int id_participacao) {
        this.id_participacao = id_participacao;
    }

    public String getStatus() {
        return status;
    }

    public int getId_b2c() {
        return id_b2c;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_b2c(int id_b2c) {
        this.id_b2c = id_b2c;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }
}