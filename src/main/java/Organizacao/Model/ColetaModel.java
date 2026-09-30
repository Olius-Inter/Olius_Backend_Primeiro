package Organizacao.Model;

import java.time.LocalDate;


// representa os dados da entidade coleta
public class ColetaModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_coleta;
    private int id_solicitacao;
    private int id_motorista;
    private LocalDate dt_coleta;
    private double volume;
    private String observacao;

    // inicializa os dados de coleta com identificador
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

    // retorna o valor de id coleta
    public int getId_coleta() {
        return id_coleta;
    }

    // atualiza o valor de id coleta
    public void setId_coleta(int id_coleta) {
        this.id_coleta = id_coleta;
    }

    // retorna o valor de id solicitacao
    public int getId_solicitacao() {
        return id_solicitacao;
    }

    // retorna o valor de id motorista
    public int getId_motorista() {
        return id_motorista;
    }

    // retorna o valor de dt coleta
    public LocalDate getDt_coleta() {
        return dt_coleta;
    }

    // retorna o valor de volume
    public Double getVolume() {
        return volume;
    }

    // retorna o valor de observacao
    public String getObservacao() {
        return observacao;
    }

    // atualiza o valor de id solicitacao
    public void setId_solicitacao(int id_solicitacao) {
        this.id_solicitacao = id_solicitacao;
    }

    // atualiza o valor de id motorista
    public void setId_motorista(int id_motorista) {
        this.id_motorista = id_motorista;
    }

    // atualiza o valor de dt coleta
    public void setDt_coleta(LocalDate dt_coleta) {
        this.dt_coleta = dt_coleta;
    }

    // atualiza o valor de volume
    public void setVolume(double volume) {
        this.volume = volume;
    }

    // atualiza o valor de observacao
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}