package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class VeiculoTerceiro implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2618040363858964085L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "veiculo_id", referencedColumnName = "id")
	private Veiculo veiculo;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column(length = 20, nullable = false)
	private String telefone;
	
	@Column(length = 20, nullable = false)
	private String parentesco;
	
	@Column(nullable = true)
	private String anexo;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id", referencedColumnName = "id")
	private Solicitacao solicitacao;
}
