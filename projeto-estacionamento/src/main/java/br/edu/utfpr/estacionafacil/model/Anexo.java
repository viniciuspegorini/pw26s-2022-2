package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Anexo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1433999675093306000L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id", referencedColumnName = "id")
	private Solicitacao solicitacao;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column(length = 250, nullable = false)
	private String contentType;
	
	@Column(length = 50, nullable = false)
	private String tipo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
    private byte[] arquivo;

}
