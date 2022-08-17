package br.edu.utfpr.estacionafacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.TipoUsuario;

	
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {
	
	TipoUsuario findByDescricao(String descricao);
}
