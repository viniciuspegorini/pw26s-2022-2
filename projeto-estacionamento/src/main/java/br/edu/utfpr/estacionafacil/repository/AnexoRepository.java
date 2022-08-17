package br.edu.utfpr.estacionafacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.Anexo;

public interface AnexoRepository extends JpaRepository<Anexo, Long>{

	public List<Anexo> findBySolicitacaoId(Long solicitacaoId);
}
