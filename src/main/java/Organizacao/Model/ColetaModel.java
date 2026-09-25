package Organizacao.Model;

import java.time.LocalDate;

// Representa a entidade Coleta do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class ColetaModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_coleta;
    private int id_solicitacao;
    private int id_motorista;
    private LocalDate dt_coleta;
    private double volume;
    private String observacao;

    // Construtor completo: cria o objeto Coleta já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public ColetaModel(int id_solicitacao,
                       int id_motorista,
                       LocalDate dt_coleta,
                       double volume,
                       String observacao) {

        this.id_solicitacao = id_solicitacao;
        this.id_motorista = id_motorista;
        this.dt_coleta = dt_coleta;
        this.volume = volume;
        this.observacao = observacao;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_coleta() {
        return id_coleta;
    }

    public void setId_coleta(int id_coleta) {
        this.id_coleta = id_coleta;
    }

    public int getId_solicitacao() {
        return id_solicitacao;
    }

    public int getId_motorista() {
        return id_motorista;
    }

    public LocalDate getDt_coleta() {
        return dt_coleta;
    }

    public Double getVolume() {
        return volume;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setId_solicitacao(int id_solicitacao) {
        this.id_solicitacao = id_solicitacao;
    }

    public void setId_motorista(int id_motorista) {
        this.id_motorista = id_motorista;
    }

    public void setDt_coleta(LocalDate dt_coleta) {
        this.dt_coleta = dt_coleta;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}