package br.edu.utfpr.estacionafacil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.repository.UsuarioRepository_;

@Service
public class UsuarioService implements UserDetailsService{
	private UsuarioRepository_ usuarioRepository;
	
	@Autowired
	public UsuarioService(UsuarioRepository_ usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);				
		
		if(usuario == null){
			throw new UsernameNotFoundException("Login inválido!");
		}
		return usuario;
	}

}
