//package com.book.exchange.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.book.exchange.service.CustomUserDetails;
//import com.book.exchange.service.CustomUserDetailsService;
//import com.book.exchange.utility.JWTUtility;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//@RestController
//@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
//@Tag(name = "TokenValidationController", description = "Token Validation")
//public class TokenValidationController {
//
//	@Autowired
//	private JWTUtility jwtUtility;
//
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
//
//	@PostMapping("/validateToken")
//	Map<String, Object> validateToken(@RequestParam("token") String token) {
//		Map<String, Object> response = new HashMap<>();
//		if (token != null && jwtUtility.validateJwtToken(token)) {
//			String username = jwtUtility.extractUsernameFromToken(token);
//
//			CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//			response.put("isValid", true);
//
//			response.put("roles", userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
//					.collect(Collectors.toSet()));
//
//		} else {
//			response.put("message", "Invalid Token");
//		}
//
//		return response;
//	}
//}
