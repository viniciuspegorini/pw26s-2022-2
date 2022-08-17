package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import br.edu.utfpr.estacionafacil.util.BooleanConverter;
import lombok.Data;

@Data
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Veiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344082845947561815L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "condutor_id", referencedColumnName = "id")
	private Condutor condutor;
	
	@ManyToOne
	@JoinColumn(name = "modelo_id", referencedColumnName = "id")
	private Modelo modelo;

	@Column(length = 10, nullable = false)
	private String placa;
	
	@Column(nullable = true)
	private String numAdesivo;
	
	@Column(length = 50, nullable = false)
	private String cor;
	
	@Convert(converter = BooleanConverter.class)
    @Column(columnDefinition = "char(1) default 'F'")
    private Boolean autorizado;
	
	@Transient
	private List<VeiculoTerceiro> veiculoTerceiros;
	
	@Transient
	private Solicitacao solicitacao;
	
}
