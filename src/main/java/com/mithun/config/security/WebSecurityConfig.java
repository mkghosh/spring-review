package com.mithun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mithun.auth.handler.CustomAuthenticationSuccessHandler;
import com.mithun.auth.jwt.JwtAuthenticationEntryPoint;
import com.mithun.auth.jwt.filters.JwtRequestFilter;
import com.mithun.db.mysql.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext context;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private UserService userService;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	public WebSecurityConfig() {
		super();
	}
	
	public WebSecurityConfig(ApplicationContext ctx) {
		this.context = ctx;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and().authorizeRequests()
					.antMatchers("/authenticate").permitAll()
					.antMatchers("/js/**", "/css/**", "/images/**", "/icons/**").permitAll()
					.antMatchers("/webjars/bootstrap/4.5.0/css/bootstrap.min.css").permitAll()
					.antMatchers("/", "/home", "/login", "/about").permitAll()
					.antMatchers("/admin/**", "/restricted").hasAnyRole("ADMIN")
					.antMatchers("/user/**").hasAnyRole("USER")
					.antMatchers("/actor/*").hasRole("MANAGER")
					.antMatchers("/leaders/**").hasRole("MANAGER")
					.antMatchers("/systems/**").hasRole("ADMIN")
					.anyRequest().authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
						.successHandler(customAuthenticationSuccessHandler)
						.permitAll()
						.failureUrl("/login-error")
				.and()
					.logout().invalidateHttpSession(false)
					.logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll()
				.and()
					.headers().frameOptions().disable()
				.and()
					.csrf().disable().exceptionHandling()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler)
				.and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/image/**", "/icon/**")
				.antMatchers("/webjars/**");
//		web.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
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
	public CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
