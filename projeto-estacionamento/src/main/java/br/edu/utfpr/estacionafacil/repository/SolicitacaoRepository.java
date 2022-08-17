package br.edu.utfpr.estacionafacil.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.model.Veiculo;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>{

	Solicitacao findById(Long id);
	
	Solicitacao findByVeiculo(Veiculo veiculo);
	
	List<Solicitacao> findByStatusOrderByDataHora(Integer status);

	List<Solicitacao> findByStatusAndVeiculoCondutorTipoUsuarioId(Integer status, Long id);

	List<Solicitacao> findByStatusAndVeiculoCondutorNomeLikeOrderByDataHora(Integer status, String nome);

	List<Solicitacao> findByStatusAndVeiculoCondutorTipoUsuarioIdAndVeiculoCondutorNomeLikeOrderByDataHora(
			Integer status, Long tipo, String nome);

	List<Solicitacao> findByDataFim(LocalDate data);
}
