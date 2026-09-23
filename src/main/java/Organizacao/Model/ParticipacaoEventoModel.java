package Organizacao.Model;

public class ParticipacaoEventoModel {

    private int id_participacao;
    private String status;
    private int id_b2c;
    private int id_evento;

    public ParticipacaoEventoModel(String status, int id_b2c, int id_evento) {
        this.status = status;
        this.id_b2c = id_b2c;
        this.id_evento = id_evento;
    }

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