package br.uefs.ecomp.mybook.model;

import java.io.Serializable;

/**
 *
 * @author Uellington Damasceno
 */
public class User implements Serializable {

    private String caminhoImagem;
    private String login;
    private String senha;
    private String nome;
    private String email;
    private String dataNascimento;
    private String endereco;
    private String telefone;

    public User(String login, String senha) {
        this(login, senha, "0", "0", "0", "0", "0");
    }

    public User(String login, String senha, String nome, String email,
            String dataN, String endereco, String telefone) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataN;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean ehValido() {
        return !(login.isEmpty() || senha.isEmpty() || nome.isEmpty()
                || email.isEmpty() || dataNascimento.isEmpty() || endereco.isEmpty());
    }

    public String caminhoPostagem() {
        return "profiles\\" + this.login + "\\postagens";
    }

    public String getCaminhoSolicitacao() {
        return "profiles\\" + this.login + "\\solicitacoes";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User outro = (User) o;
            if (this.hashCode() == outro.hashCode()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return this.login;
    }
}
