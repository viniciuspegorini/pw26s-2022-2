package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Entity
@Data
public class EntradaTerceiros implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8797878403147676696L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=8, nullable=false)
	private String placaVeiculo;
	
	@Column(length=50)
	private String localDestino;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraEntrada;
}
