package Organizacao.Model;

import java.time.LocalDate;


// representa os dados da entidade ponto de entrega voluntaria
public class PevModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_pev;
    private int id_endereco;
    private int id_usuario_b2b;
    private int id_usuario_b2c;
    private String qr_code;
    private String status;
    private LocalDate dt_aprovacao;

    // inicializa os dados de ponto de entrega voluntaria com identificador
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

    // retorna o valor de id pev
    public int getId_pev() {
        return id_pev;
    }

    // atualiza o valor de id pev
    public void setId_pev(int id_pev) {
        this.id_pev = id_pev;
    }

    // retorna o valor de id endereco
    public int getId_endereco() {
        return id_endereco;
    }

    // retorna o valor de id usuario b b
    public int getId_usuario_b2b() {
        return id_usuario_b2b;
    }

    // retorna o valor de id usuario b c
    public int getId_usuario_b2c() {
        return id_usuario_b2c;
    }

    // retorna o valor de qr code
    public String getQr_code() {
        return qr_code;
    }

    // retorna o valor de status
    public String getStatus() {
        return status;
    }

    // retorna o valor de dt aprovacao
    public LocalDate getDt_aprovacao() {
        return dt_aprovacao;
    }

    // atualiza o valor de id endereco
    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    // atualiza o valor de id usuario b b
    public void setId_usuario_b2b(int id_usuario_b2b) {
        this.id_usuario_b2b = id_usuario_b2b;
    }

    // atualiza o valor de id usuario b c
    public void setId_usuario_b2c(int id_usuario_b2c) {
        this.id_usuario_b2c = id_usuario_b2c;
    }

    // atualiza o valor de qr code
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    // atualiza o valor de status
    public void setStatus(String status) {
        this.status = status;
    }

    // atualiza o valor de dt aprovacao
    public void setDt_aprovacao(LocalDate dt_aprovacao) {
        this.dt_aprovacao = dt_aprovacao;
    }
}