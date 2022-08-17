package br.edu.utfpr.estacionafacil.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;

@Entity
@Data
public class Usuario implements UserDetails, Serializable{
	private static final long serialVersionUID = 1L;

	private static final BCryptPasswordEncoder bCry = 
				new BCryptPasswordEncoder(10);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column(length = 50, nullable = false)
	private String username;
	
	@Column(length = 512, nullable = false)
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "tipo_usuario_id", referencedColumnName = "id")
	private TipoUsuario tipoUsuario;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "condutor_id", referencedColumnName = "id")
	private Condutor condutor;
	
	@ManyToMany(cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER)
	private Set<Permissao> permissoes;
	
	@Override
	public Collection<? extends GrantedAuthority> 
										getAuthorities() {
		List<GrantedAuthority> auto = new ArrayList<>();
		auto.addAll(getPermissoes());
		
		return auto;
	}
	
	public void addPermissao(Permissao permissao){
		if (permissoes == null){
			permissoes = new HashSet<>();
		}
		permissoes.add(permissao);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getEncodedPassword(String pass){
		if (!pass.isEmpty()){
			return bCry.encode(pass);
		}
		return pass;
	}
	
	@Override
	public String toString() {
		return "Usuário: " + this.getUsername();
	}

}
