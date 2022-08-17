package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Objects;

/**
 *
 * @author Vinicius
 */
public class UsuarioLdap implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3865962101605008475L;
	
	private boolean logado;
    private String dn;
    private String nome;
    private String sobrenome;
    private String email;
    private String usuario; //Ra para aluno; usuário do sistema para professor
    private String cpf;
    private String campus;
    private String curso;
    private String mensagem;
    
    public UsuarioLdap() {
    }

    public UsuarioLdap(String nome, String sobrenome, String email, String usuario, String cpf, String campus, String curso) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.usuario = usuario;
        this.cpf = cpf;
        this.campus = campus;
        this.curso = curso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.usuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioLdap other = (UsuarioLdap) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioLdap{" + "nome=" + nome + ", sobrenome=" + sobrenome + ", "
                + "email=" + email + ", usuario=" + usuario + ", cpf=" + cpf + ", "
                + "campus=" + campus + ", curso=" + curso + '}';
    }
}
