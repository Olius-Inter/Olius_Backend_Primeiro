package Organizacao.Model;

public class CarteiraModel {

    private int id_carteira;
    private int pontuacao;
    private String patente;
    private String nivel;
    private int id_b2c;

    // Construtor sem ID (INSERT)
    public CarteiraModel(int pontuacao, String patente, String nivel, int id_b2c) {
        this.pontuacao = pontuacao;
        this.patente = patente;
        this.nivel = nivel;
        this.id_b2c = id_b2c;
    }

    // Construtor com ID (READ/UPDATE)
    public CarteiraModel(int id_carteira, int pontuacao, String patente, String nivel, int id_b2c) {
        this.id_carteira = id_carteira;
        this.pontuacao = pontuacao;
        this.patente = patente;
        this.nivel = nivel;
        this.id_b2c = id_b2c;
    }

    public int getId_carteira() {
        return id_carteira;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getPatente() {
        return patente;
    }

    public String getNivel() {
        return nivel;
    }

    public int getId_b2c() {
        return id_b2c;
    }

    public void setId_carteira(int id_carteira) {
        this.id_carteira = id_carteira;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setId_b2c(int id_b2c) {
        this.id_b2c = id_b2c;
    }
}
