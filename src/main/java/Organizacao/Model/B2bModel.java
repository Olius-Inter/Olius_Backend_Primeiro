package Organizacao.Model;


// representa os dados da entidade empresa b dois b
public class B2bModel {
    // armazena os atributos e relacionamentos da entidade

    private int id_usuario;
    private String cnpj;
    private String razao_social;
    private String nome_empresa;
    private String telefone;
    private Integer id_endereco;
    private Integer id_usuario_vinculado;
    private String email;
    private String senha;

    // inicializa os dados de empresa b dois b
    public B2bModel(String cnpj, String razao_social,
                    String nome_empresa, String telefone,
                    Integer id_endereco) {

        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_empresa = nome_empresa;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // inicializa os dados de empresa b dois b com identificador
    public B2bModel(int id_usuario, String cnpj, String razao_social, String nome_empresa, String telefone, Integer id_endereco) {
        this.id_usuario = id_usuario;
        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_empresa = nome_empresa;
        this.telefone = telefone;
        this.id_endereco = id_endereco;
    }

    // retorna o valor de id usuario
    public int getId_usuario() {
        return id_usuario;
    }

    // atualiza o identificador gerado no cadastro
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    // retorna o valor de cnpj
    public String getCnpj() {
        return cnpj;
    }

    // retorna o valor de razao social
    public String getRazao_social() {
        return razao_social;
    }

    // retorna o valor de nome fantasia
    public String getNome_empresa() {
        return nome_empresa;
    }

    // retorna o valor de telefone
    public String getTelefone() {
        return telefone;
    }

    // retorna o valor de id endereco
    public Integer getId_endereco() {
        return id_endereco;
    }

    // atualiza o valor de cnpj
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // atualiza o valor de razao social
    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    // atualiza o valor de nome fantasia
    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    // atualiza o valor de telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // atualiza o valor de id endereco
    public void setId_endereco(Integer id_endereco) {
        this.id_endereco = id_endereco;
    }
    // usuario que guarda os dados comuns deste cadastro de empresa
    public Integer getId_usuario_vinculado() {
        return id_usuario_vinculado;
    }

    public void setId_usuario_vinculado(Integer id_usuario_vinculado) {
        this.id_usuario_vinculado = id_usuario_vinculado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private String plano;

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

}