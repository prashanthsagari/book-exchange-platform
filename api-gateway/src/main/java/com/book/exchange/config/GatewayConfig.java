package com.book.exchange.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.book.exchange.security.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()

		        .route("service-discovery", r -> r.path("/service-discovery/**")
		                .filters(f -> f.addRequestHeader("X-Gateway-Id", "api-gateway")
		                        .addRequestHeader("X-Gateway-Path", "/api-gateway"))
		                .uri("lb://service-discovery"))
		        .route("user-mgmt", r -> r.path("/user-mgmt/**").filters(f -> {
			        f.filter(filter);
			        f.circuitBreaker(config -> config.setName("userServiceCircuitBreaker")
			                .setFallbackUri("forward:/fallback/user-mgmt"));
			        f.addRequestHeader("X-Gateway-Id", "api-gateway");
			        f.addRequestHeader("X-Gateway-Path", "/api-gateway");
			        return f;
		        }).uri("lb://user-mgmt")).build();
	}
}
