package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Infracao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8477682021186429034L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "veiculo_id", referencedColumnName = "id")
	private Veiculo veiculo;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@Column
	private InfracaoType tipoInfracao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraEmailEnviado;
	
	@Column
	private boolean justificado; 
	
	@Column
	private String justificativa;
}
