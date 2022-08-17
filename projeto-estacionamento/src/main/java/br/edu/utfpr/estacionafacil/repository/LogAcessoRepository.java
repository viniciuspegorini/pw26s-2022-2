package br.edu.utfpr.estacionafacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.estacionafacil.model.LogAcesso;

public interface LogAcessoRepository extends JpaRepository<LogAcesso, Long> {

}
