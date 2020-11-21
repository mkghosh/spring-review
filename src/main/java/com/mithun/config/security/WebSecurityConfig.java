package com.mithun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mithun.auth.handler.CustomAuthenticationSuccessHandler;
import com.mithun.db.mysql.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private UserService userService;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	// https://www.javainuse.com/spring/boot_security_jdbc_authentication
//	@Autowired
//	@Qualifier("mysqlDataSource")
//	private DataSource mysqlDataSource;

	public WebSecurityConfig() {
		super();
	}

	// Enable jdbc authentication

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
//		.jdbcAuthentication().dataSource(mysqlDataSource);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and().authorizeRequests()
					.antMatchers("/js/**", "/css/**", "/images/**", "/icons/**").permitAll()
					.antMatchers("/webjars/bootstrap/4.5.0/css/bootstrap.min.css").permitAll()
					.antMatchers("/", "/home", "/login", "/about").permitAll()
					.antMatchers("/admin/**", "/restricted").hasAnyRole("ADMIN")
					.antMatchers("/user/**").hasAnyRole("USER")
					.antMatchers("/").hasRole("EMPLOYEE")
					.antMatchers("/leaders/**").hasRole("MANAGER")
					.antMatchers("/systems/**").hasRole("ADMIN")
					.anyRequest().authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
//						.loginProcessingUrl("/authenticateTheUser")
						.successHandler(customAuthenticationSuccessHandler)
						.permitAll()
//						.defaultSuccessUrl("/home")
//						.failureUrl("/login-error")
				.and()
					.logout().invalidateHttpSession(false)
					.logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll()
				.and()
					.headers().frameOptions().disable()
				.and()
					.csrf().disable().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/image/**", "/icon/**")
				.antMatchers("/webjars/**");
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
}
