package Organizacao.Model;

// Movimentação de pontos de uma carteira. Vem de uma coleta (empresa) OU de uma entrega (cliente).
public class PontosMovimentacaoModel {

    private int idMovimentacao;
    private int quantidadePontos;
    private int idCarteira;
    private Integer idColeta;   // null quando a origem é uma entrega
    private Integer idEntrega;  // null quando a origem é uma coleta

    public PontosMovimentacaoModel(int quantidadePontos, int idCarteira, Integer idColeta, Integer idEntrega) {
        this.quantidadePontos = quantidadePontos;
        this.idCarteira = idCarteira;
        this.idColeta = idColeta;
        this.idEntrega = idEntrega;
    }

    public PontosMovimentacaoModel(int idMovimentacao, int quantidadePontos, int idCarteira,
                                   Integer idColeta, Integer idEntrega) {
        this(quantidadePontos, idCarteira, idColeta, idEntrega);
        this.idMovimentacao = idMovimentacao;
    }

    public int getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(int idMovimentacao) { this.idMovimentacao = idMovimentacao; }
    public int getQuantidadePontos() { return quantidadePontos; }
    public void setQuantidadePontos(int quantidadePontos) { this.quantidadePontos = quantidadePontos; }
    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }
    public Integer getIdColeta() { return idColeta; }
    public void setIdColeta(Integer idColeta) { this.idColeta = idColeta; }
    public Integer getIdEntrega() { return idEntrega; }
    public void setIdEntrega(Integer idEntrega) { this.idEntrega = idEntrega; }
}
