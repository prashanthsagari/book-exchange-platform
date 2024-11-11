package com.book.exchange.security;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.book.exchange.config.JWTUtility;
import com.book.exchange.exception.InvalidTokenException;
import com.book.exchange.exception.NoTokenException;

import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Value("${book.jwtSecret}")
	private String secret;

	@Autowired
	private JWTUtility jwtUtility;

	private final AntPathMatcher pathMatcher = new AntPathMatcher(); // AntPathMatcher for pattern matching

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
		ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();

		// whitelist urls
		// Todo can be database driven
		final List<String> apiEndpoints = List.of("/user-mgmt/swagger-ui/**", "/user-mgmt/v3/api-docs/**",
		        "/user-mgmt/swagger-resources/**", "/user-mgmt/api/v1/auth/**");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
		        .noneMatch(uri -> pathMatcher.match(uri, r.getURI().getPath()));

		final String authHeader = request.getHeaders().getFirst("Authorization");

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatusCode(HttpStatus.OK);
			return chain.filter(exchange);
		}
		System.out.println(request.getURI().getPath());
		// If the endpoint is not whitelisted, perform JWT validation
		if (isApiSecured.test(request)) {
			try {
				if (authHeader == null || (!authHeader.startsWith("Bearer "))) {
					throw new NoTokenException("Missing or invalid Authorization header");
				}
				// Extract token without "Bearer "
				final String token = authHeader.substring(7);
				if (token != null && jwtUtility.validateJwtToken(token)) {
					String username = jwtUtility.extractUsernameFromToken(token);
//					request to add username as a header
					request = exchange.getRequest().mutate().header("username", username).build();

					// Create a new exchange with the mutated request and continue the chain
					return chain.filter(exchange.mutate().request(request).build());
				} else {
					throw new InvalidTokenException("Invalid token");
				}

			} catch (JwtException | NoTokenException e) {
				// Log the error if necessary
				return Mono.error(new InvalidTokenException("Invalid or missing token"));
			}
		}
		// testing purpose
//		exchange.getRequest().mutate().header("username", "Prashanth Sagari").build();
		return chain.filter(exchange);
	}

}
