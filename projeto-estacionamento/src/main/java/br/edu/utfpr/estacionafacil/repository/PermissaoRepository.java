package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.utfpr.estacionafacil.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

	Permissao findByPermissao(String permissao);

	@Query(value = "Select p.* From permissao p Inner Join usuario_permissoes sp on sp.permissoes_id = p.id "
			+ "where sp.usuario_id =?1", nativeQuery = true)
	List<Permissao> getListaPermissoes(Long idServidor);

}
