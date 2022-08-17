package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Modelo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3830309820335741702L;

	@Id
	//Dados vem da tabela FIPE por isso os códs são gerados de maneira manual	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "marca_id", referencedColumnName = "id")
	private Marca marca;

}
