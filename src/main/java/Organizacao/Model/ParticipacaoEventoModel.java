package Organizacao.Model;

// Representa a entidade ParticipacaoEvento do sistema.

public class ParticipacaoEventoModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_participacao;
    private String status;
    private int id_b2c;
    private int id_evento;

    // Construtor: cria o objeto ParticipacaoEvento já com todos os dados informados
    public ParticipacaoEventoModel(String status, int id_b2c, int id_evento) {
        this.status = status;
        this.id_b2c = id_b2c;
        this.id_evento = id_evento;
    }

    // Getters e setters
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