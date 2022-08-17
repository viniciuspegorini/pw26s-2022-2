package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.model.Veiculo;
import br.edu.utfpr.estacionafacil.model.VeiculoTerceiro;

	
public interface VeiculoTerceiroRepository extends JpaRepository<VeiculoTerceiro, Long> {
	

	List<VeiculoTerceiro> findByVeiculo(Veiculo veiculo);
	VeiculoTerceiro findBySolicitacao(Solicitacao solicitacao);
}
