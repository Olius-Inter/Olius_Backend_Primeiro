package Organizacao.Model;

// Carteira de pontos: pertence a um cliente B2C ou a uma empresa B2B (nunca aos dois)
public class CarteiraModel {

    private int idCarteira;
    private int pontosTotais;
    private String patente;   // Ouro, Prata ou Bronze
    private int nivel;
    private Integer idB2c;    // null quando a carteira é de empresa
    private Integer idB2b;    // null quando a carteira é de cliente

    public CarteiraModel(int pontosTotais, String patente, int nivel, Integer idB2c, Integer idB2b) {
        this.pontosTotais = pontosTotais;
        this.patente = patente;
        this.nivel = nivel;
        this.idB2c = idB2c;
        this.idB2b = idB2b;
    }

    public CarteiraModel(int idCarteira, int pontosTotais, String patente, int nivel, Integer idB2c, Integer idB2b) {
        this(pontosTotais, patente, nivel, idB2c, idB2b);
        this.idCarteira = idCarteira;
    }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }
    public int getPontosTotais() { return pontosTotais; }
    public void setPontosTotais(int pontosTotais) { this.pontosTotais = pontosTotais; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public Integer getIdB2c() { return idB2c; }
    public void setIdB2c(Integer idB2c) { this.idB2c = idB2c; }
    public Integer getIdB2b() { return idB2b; }
    public void setIdB2b(Integer idB2b) { this.idB2b = idB2b; }
}
