package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Marca;
import br.edu.utfpr.estacionafacil.model.Modelo;

	
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
	
	List<Modelo> findByMarcaOrderByNome(Marca marca);
}
