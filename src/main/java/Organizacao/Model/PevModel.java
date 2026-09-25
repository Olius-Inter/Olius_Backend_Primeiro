package Organizacao.Model;

import java.time.LocalDate;

// Representa a entidade Pev do sistema.
// Cada instância desta classe corresponde a um registro da tabela
// correspondente no banco de dados, sendo utilizada para transportar
// os dados entre o DAO (persistência) e o Servlet (camada web).
public class PevModel {

    // Atributos que representam as colunas da tabela no banco de dados
    private int id_pev;
    private int id_endereco;
    private int id_usuario_b2b;
    private int id_usuario_b2c;
    private String qr_code;
    private String status;
    private LocalDate dt_aprovacao;

    // Construtor completo: cria o objeto Pev já com todos os
    // dados informados (usado, por exemplo, ao montar o objeto a partir
    // do ResultSet no DAO ou dos parâmetros recebidos no Servlet).
    public PevModel(int id_endereco, int id_usuario_b2b,
                    int id_usuario_b2c, String qr_code,
                    String status, LocalDate dt_aprovacao) {

        this.id_endereco = id_endereco;
        this.id_usuario_b2b = id_usuario_b2b;
        this.id_usuario_b2c = id_usuario_b2c;
        this.qr_code = qr_code;
        this.status = status;
        this.dt_aprovacao = dt_aprovacao;
    }

    // Getters e setters: expõem e permitem alterar cada atributo (encapsulamento)
    public int getId_pev() {
        return id_pev;
    }

    public void setId_pev(int id_pev) {
        this.id_pev = id_pev;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public int getId_usuario_b2b() {
        return id_usuario_b2b;
    }

    public int getId_usuario_b2c() {
        return id_usuario_b2c;
    }

    public String getQr_code() {
        return qr_code;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDt_aprovacao() {
        return dt_aprovacao;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public void setId_usuario_b2b(int id_usuario_b2b) {
        this.id_usuario_b2b = id_usuario_b2b;
    }

    public void setId_usuario_b2c(int id_usuario_b2c) {
        this.id_usuario_b2c = id_usuario_b2c;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDt_aprovacao(LocalDate dt_aprovacao) {
        this.dt_aprovacao = dt_aprovacao;
    }
}