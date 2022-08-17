package br.edu.utfpr.estacionafacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Condutor;


public interface CondutorRepository extends JpaRepository<Condutor, Long>{
	Condutor findByRegistro(String registro);
}