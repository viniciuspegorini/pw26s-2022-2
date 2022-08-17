package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Marca implements Serializable{
	private static final long serialVersionUID = 5418792284142479916L;

	@Id
	//Dados vem da tabela FIPE por isso os códs são gerados de maneira manual
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 1, nullable = true)
	private String tipo;

}
