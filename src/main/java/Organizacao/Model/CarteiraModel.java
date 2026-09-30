package Organizacao.Model;


// representa os dados da entidade carteira de pontos
public class CarteiraModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_carteira;
    private int pontuacao;
    private String patente;
    private String nivel;
    private int id_b2c;

    // inicializa os dados de carteira de pontos com identificador
    public CarteiraModel(int pontuacao, String patente, String nivel, int id_b2c) {
        this.pontuacao = pontuacao;
        this.patente = patente;
        this.nivel = nivel;
        this.id_b2c = id_b2c;
    }

    // inicializa os dados de carteira de pontos com identificador
    public CarteiraModel(int id_carteira, int pontuacao, String patente, String nivel, int id_b2c) {
        this.id_carteira = id_carteira;
        this.pontuacao = pontuacao;
        this.patente = patente;
        this.nivel = nivel;
        this.id_b2c = id_b2c;
    }

    // retorna o valor de id carteira
    public int getId_carteira() {
        return id_carteira;
    }

    // retorna o valor de pontuacao
    public int getPontuacao() {
        return pontuacao;
    }

    // retorna o valor de patente
    public String getPatente() {
        return patente;
    }

    // retorna o valor de nivel
    public String getNivel() {
        return nivel;
    }

    // retorna o valor de id b c
    public int getId_b2c() {
        return id_b2c;
    }

    // atualiza o valor de id carteira
    public void setId_carteira(int id_carteira) {
        this.id_carteira = id_carteira;
    }

    // atualiza o valor de pontuacao
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    // atualiza o valor de patente
    public void setPatente(String patente) {
        this.patente = patente;
    }

    // atualiza o valor de nivel
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    // atualiza o valor de id b c
    public void setId_b2c(int id_b2c) {
        this.id_b2c = id_b2c;
    }
}
