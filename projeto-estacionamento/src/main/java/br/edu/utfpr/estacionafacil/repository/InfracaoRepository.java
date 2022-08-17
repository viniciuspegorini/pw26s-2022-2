package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Infracao;
import br.edu.utfpr.estacionafacil.model.Usuario;

public interface InfracaoRepository extends JpaRepository<Infracao, Long> {

	List<Infracao> findByUsuarioOrderByIdAsc(Usuario user);

	Infracao findByIdAndUsuario(Long id, Usuario user);

}
