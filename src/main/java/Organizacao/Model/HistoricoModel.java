package Organizacao.Model;

import java.time.LocalDate;

// Representa a entidade Historico do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class HistoricoModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_historico;
    private int id_usuario;
    private String tp_evento;
    private String descricao;
    private LocalDate dt_evento;

    // Construtor completo: cria o objeto Historico já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public HistoricoModel(int id_usuario, String tp_evento,
                          String descricao, LocalDate dt_evento) {

        this.id_usuario = id_usuario;
        this.tp_evento = tp_evento;
        this.descricao = descricao;
        this.dt_evento = dt_evento;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_historico() {
        return id_historico;
    }

    public void setId_historico(int id_historico) {
        this.id_historico = id_historico;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getTp_evento() {
        return tp_evento;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDt_evento() {
        return dt_evento;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setTp_evento(String tp_evento) {
        this.tp_evento = tp_evento;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDt_evento(LocalDate dt_evento) {
        this.dt_evento = dt_evento;
    }
}