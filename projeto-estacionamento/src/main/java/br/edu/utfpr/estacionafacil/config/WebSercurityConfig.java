package br.edu.utfpr.estacionafacil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.utfpr.estacionafacil.repository.UsuarioRepository_;
import br.edu.utfpr.estacionafacil.service.UsuarioService;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSercurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioRepository_ usuarioRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling().accessDeniedPage("/erro403").and()
			.formLogin().loginPage("/login")
			.defaultSuccessUrl("/index")
			.failureUrl("/login?error=bad_credentials")
			.permitAll().and()
			.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/view/**").hasRole("ADMIN")
			.antMatchers("/condutor/**").hasRole("USER");
	}
	
	@Bean
	public UserDetailsService userDetailsService(){
		return new UsuarioService(usuarioRepository);
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
											throws Exception {
		auth.userDetailsService( userDetailsService() )
				.passwordEncoder( passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**");
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/h2-console/**");
	}
}








