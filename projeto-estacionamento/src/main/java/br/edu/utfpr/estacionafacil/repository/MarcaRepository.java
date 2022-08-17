package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Marca;

	
public interface MarcaRepository extends JpaRepository<Marca, Long> {
	

	public List<Marca> findByOrderByNome();
}
