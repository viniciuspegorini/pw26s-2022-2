package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Condutor;
import br.edu.utfpr.estacionafacil.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	List<Veiculo> findByPlaca(String placa);
	
	List<Veiculo> findByCondutor(Condutor condutor);

	List<Veiculo> findByPlacaAndCondutorId(String placa, Long id);

	List<Veiculo> findByNumAdesivoAndIdNotAndCondutorTipoUsuarioId(String numAdesivo, Long veiculoId, Long tipoUsuarioId);
}
