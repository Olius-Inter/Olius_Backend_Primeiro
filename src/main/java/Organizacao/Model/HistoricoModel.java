package Organizacao.Model;

import java.time.LocalDate;


// representa os dados da entidade historico de usuario
public class HistoricoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_historico;
    private int id_usuario;
    private String tp_evento;
    private String descricao;
    private LocalDate dt_evento;

    // inicializa os dados de historico de usuario com identificador
    public HistoricoModel(int id_usuario, String tp_evento,
                          String descricao, LocalDate dt_evento) {

        this.id_usuario = id_usuario;
        this.tp_evento = tp_evento;
        this.descricao = descricao;
        this.dt_evento = dt_evento;
    }

    // retorna o valor de id historico
    public int getId_historico() {
        return id_historico;
    }

    // atualiza o valor de id historico
    public void setId_historico(int id_historico) {
        this.id_historico = id_historico;
    }

    // retorna o valor de id usuario
    public int getId_usuario() {
        return id_usuario;
    }

    // retorna o valor de tp evento
    public String getTp_evento() {
        return tp_evento;
    }

    // retorna o valor de descricao
    public String getDescricao() {
        return descricao;
    }

    // retorna o valor de dt evento
    public LocalDate getDt_evento() {
        return dt_evento;
    }

    // atualiza o valor de id usuario
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    // atualiza o valor de tp evento
    public void setTp_evento(String tp_evento) {
        this.tp_evento = tp_evento;
    }

    // atualiza o valor de descricao
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // atualiza o valor de dt evento
    public void setDt_evento(LocalDate dt_evento) {
        this.dt_evento = dt_evento;
    }
}