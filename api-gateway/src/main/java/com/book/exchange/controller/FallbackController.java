package com.book.exchange.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/fallback/user-mgmt")
	public String userMgmtFallback() {
		return "User Service is currently unavailable. Please try again later.";
	}
}