package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Ldap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4985772290955902695L;
	private boolean logado;
    private String dn; //é utilizado apenas para o login
    private String nome;
    private String sobrenome;
    private String email;
    private String usuario; //Ra para aluno; usuário do sistema para professor
    private String cpf;
    private String campus;
    private String curso;
    private String mensagem;
}
