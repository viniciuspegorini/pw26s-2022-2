package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Solicitacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7960213736899301280L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "veiculo_id", referencedColumnName = "id")
	private Veiculo veiculo;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private Date dataHora;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	@Column(length = 1000)
	private String observacoes;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = true)
	private LocalDate dataFim;
	
	public boolean isExpirado() {
		return this.dataFim != null ? (java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), this.dataFim) <= 7) : false;
	}
	
}
