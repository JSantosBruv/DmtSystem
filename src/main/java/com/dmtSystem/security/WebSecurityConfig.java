package com.dmtSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetailsService userWorkerDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/PontoDeVenda/**")
				.hasAnyRole("ADMIN", "PONTOVENDA").antMatchers(HttpMethod.POST, "/PontoDeVenda/**")
				.hasAnyRole("ADMIN", "PONTOVENDA").antMatchers(HttpMethod.GET, "/Alfaiataria/**")
				.hasAnyRole("ADMIN", "ALFAIATARIA").antMatchers(HttpMethod.POST, "/Alfaiataria/**")
				.hasAnyRole("ADMIN", "ALFAIATARIA").antMatchers(HttpMethod.GET, "/Administrador/**").hasAnyRole("ADMIN").antMatchers(HttpMethod.POST, "/Administrador/**").hasAnyRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/User/**").hasAnyRole("ADMIN", "PONTOVENDA","ALFAIATARIA")
				.antMatchers(HttpMethod.GET, "/User/**").hasAnyRole("ADMIN", "PONTOVENDA","ALFAIATARIA").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logoutAcc")).and().exceptionHandling()
				.accessDeniedPage("/noPermission");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userWorkerDetailsService).passwordEncoder(new BCryptPasswordEncoder(10));
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**", "/style/**", "/images/**");
	}
}