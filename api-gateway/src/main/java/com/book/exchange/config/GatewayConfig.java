package com.book.exchange.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.book.exchange.security.JwtAuthenticationFilter;



@Configuration
@CrossOrigin
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		// Specify the allowed origin, replace with your frontend URL
		corsConfig.addAllowedOrigin("http://localhost:5173"); // Use your actual frontend URL here
		corsConfig.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
		corsConfig.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
		corsConfig.addAllowedHeader("*"); // Allow all headers
		  // Setting up the source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
		return new CorsWebFilter(source);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()

		        .route("service-discovery", r -> r.path("/service-discovery/**")
		                .filters(f -> f.addRequestHeader("X-Gateway-Id", "api-gateway")
		                        .addRequestHeader("X-Gateway-Path", "/api-gateway"))
		                .uri("lb://service-discovery"))
		        .route("book-mgmt", r -> r.path("/book-mgmt/**").filters(f -> {
			        f.filter(jwtAuthenticationFilter);
			        f.circuitBreaker(config -> config.setName("userServiceCircuitBreaker")
			                .setFallbackUri("forward:/fallback/book-mgmt"));
			        f.addRequestHeader("X-Gateway-Id", "api-gateway");
			        f.addRequestHeader("X-Gateway-Path", "/api-gateway");
			        return f;
		        }).uri("lb://book-mgmt"))
		        .route("user-mgmt", r -> r.path("/user-mgmt/**").filters(f -> {
			        f.filter(jwtAuthenticationFilter);
			        f.circuitBreaker(config -> config.setName("userServiceCircuitBreaker")
			                .setFallbackUri("forward:/fallback/user-mgmt"));
			        f.addRequestHeader("X-Gateway-Id", "api-gateway");
			        f.addRequestHeader("X-Gateway-Path", "/api-gateway");
			        return f;
		        }).uri("lb://user-mgmt"))
		        .build();
	}
}
