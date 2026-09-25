package Organizacao.Model;

import java.time.LocalDate;

// Representa a entidade EntregaPev do sistema.

public class EntregaPevModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_entrega;
    private int id_usuario_b2c;
    private int id_pev;
    private double qtd_litros;
    private int pontos_gerados;
    private LocalDate dt_entrega;

    // Construtor: cria o objeto EntregaPev já com todos os dados informados
    public EntregaPevModel(int id_usuario_b2c,
                           int id_pev,
                           double qtd_litros,
                           int pontos_gerados,
                           LocalDate dt_entrega) {

        this.id_usuario_b2c = id_usuario_b2c;
        this.id_pev = id_pev;
        this.qtd_litros = qtd_litros;
        this.pontos_gerados = pontos_gerados;
        this.dt_entrega = dt_entrega;
    }

    // Getters e setters
    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public int getId_usuario_b2c() {
        return id_usuario_b2c;
    }

    public int getId_pev() {
        return id_pev;
    }

    public double getQtd_litros() {
        return qtd_litros;
    }

    public int getPontos_gerados() {
        return pontos_gerados;
    }

    public LocalDate getDt_entrega() {
        return dt_entrega;
    }

    public void setId_usuario_b2c(int id_usuario_b2c) {
        this.id_usuario_b2c = id_usuario_b2c;
    }

    public void setId_pev(int id_pev) {
        this.id_pev = id_pev;
    }

    public void setQtd_litros(double qtd_litros) {
        this.qtd_litros = qtd_litros;
    }

    public void setPontos_gerados(int pontos_gerados) {
        this.pontos_gerados = pontos_gerados;
    }

    public void setDt_entrega(LocalDate dt_entrega) {
        this.dt_entrega = dt_entrega;
    }
}