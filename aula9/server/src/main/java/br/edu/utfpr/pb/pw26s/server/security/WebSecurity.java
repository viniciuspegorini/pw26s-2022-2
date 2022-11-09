package br.edu.utfpr.pb.pw26s.server.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurity {

    private final AuthUserService authUserService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurity(AuthUserService authUserService,
                       AuthenticationEntryPoint authenticationEntryPoint) {
        this.authUserService = authUserService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // authenticationManager -> responsável pela autenticação dos usuários
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authUserService)
                .passwordEncoder( passwordEncoder() );
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.headers().frameOptions().disable();

        http.cors()
            .and().csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers( "/error/**").permitAll()
                .antMatchers( "/auth/**").permitAll()
                .antMatchers( "/categories/**").hasAnyRole("USER", "ADMIN")
                .antMatchers( "/products/**").hasAnyRole("ADMIN")

                .antMatchers("/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v2/api-docs",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationManager(authenticationManager)
                //Filtro da Autenticação
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authUserService) )
                //Filtro da Autorizaçao
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authUserService) )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
