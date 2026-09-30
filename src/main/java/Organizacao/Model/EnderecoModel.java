package Organizacao.Model;


// representa os dados da entidade endereco
public class EnderecoModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_endereco;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    // inicializa os dados de endereco
    public EnderecoModel(String cep, String logradouro,
                         String numero, String complemento,
                         String bairro, String cidade,
                         String estado, String pais) {

        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    // retorna o valor de id endereco
    public int getId_endereco() {
        return id_endereco;
    }

    // atualiza o valor de id endereco
    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    // retorna o valor de cep
    public String getCep() {
        return cep;
    }

    // retorna o valor de logradouro
    public String getLogradouro() {
        return logradouro;
    }

    // retorna o valor de numero
    public String getNumero() {
        return numero;
    }

    // retorna o valor de complemento
    public String getComplemento() {
        return complemento;
    }

    // retorna o valor de bairro
    public String getBairro() {
        return bairro;
    }

    // retorna o valor de cidade
    public String getCidade() {
        return cidade;
    }

    // retorna o valor de estado
    public String getEstado() {
        return estado;
    }

    // retorna o valor de pais
    public String getPais() {
        return pais;
    }

    // atualiza o valor de cep
    public void setCep(String cep) {
        this.cep = cep;
    }

    // atualiza o valor de logradouro
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    // atualiza o valor de numero
    public void setNumero(String numero) {
        this.numero = numero;
    }

    // atualiza o valor de complemento
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    // atualiza o valor de bairro
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // atualiza o valor de cidade
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    // atualiza o valor de estado
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // atualiza o valor de pais
    public void setPais(String pais) {
        this.pais = pais;
    }
}