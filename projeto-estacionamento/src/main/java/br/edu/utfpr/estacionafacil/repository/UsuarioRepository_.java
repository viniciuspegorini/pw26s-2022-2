package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Usuario;

	
public interface UsuarioRepository_ extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);

	List<Usuario> findByNomeContaining(String nome);
}
