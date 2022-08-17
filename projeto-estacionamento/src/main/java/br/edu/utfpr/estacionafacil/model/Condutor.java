package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.Data;

@Entity
@Data
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Condutor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5036362869543233014L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 100, nullable = true)
	private String email;
	
	@Column(length = 50, nullable = true)
	private String registro;
	
	@Column(length = 30, nullable = false)
	private String telefone;
	
	@Column(nullable = true)
	private Boolean vagaEspecial;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_usuario_id ", referencedColumnName = "id")
	private TipoUsuario tipoUsuario;
	
}
