package com.book.exchange.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//	private static final String[] OPEN_URLS = { "/", "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**",
//	        "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/api/v1/profile/**" };

//	@Autowired
//	CustomUserDetailsService customUserDetailsService;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;

//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}

//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//		authProvider.setUserDetailsService(customUserDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder());
//
//		return authProvider;
//	}

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//		return authConfig.getAuthenticationManager();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF protection if necessary
		        .cors(cors -> cors.disable()) // Disable CORS protection
		        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll() // Allow all requests
				);
		return http.build(); // Build and return the SecurityFilterChain
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		http.csrf(csrf -> csrf.disable()) // Disable CSRF protection if necessary
//		        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//		        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		        .cors(cors -> cors.disable()) // Disable CORS protection
//		        .authorizeHttpRequests(
//		                authorize -> authorize.requestMatchers(OPEN_URLS).permitAll().anyRequest().authenticated())
//		        .authenticationProvider(authenticationProvider())
//		        .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//		return http.build(); // Build and return the SecurityFilterChain
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
