package com.book.exchange.controller;

import org.springframework.cloud.gateway.support.ServiceUnavailableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/fallback/user-mgmt")
	public String userMgmtFallback1() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("User Service is currently unavailable. Please try again later.");
	}

	@PostMapping("/fallback/user-mgmt")
	public String userMgmtFallback2() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("User Service is currently unavailable. Please try again later.");
	}

	@PutMapping("/fallback/user-mgmt")
	public String userMgmtFallback3() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("User Service is currently unavailable. Please try again later.");
	}

	@DeleteMapping("/fallback/user-mgmt")
	public String userMgmtFallback4() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("User Service is currently unavailable. Please try again later.");
	}

	@GetMapping("/fallback/book-mgmt")
	public String userMgmtFallback5() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("Book Management Service is currently unavailable. Please try again later.");
	}

	@PostMapping("/fallback/book-mgmt")
	public String userMgmtFallback6() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("Book Management Service is currently unavailable. Please try again later.");
	}

	@PutMapping("/fallback/book-mgmt")
	public String userMgmtFallback7() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("Book Management Service is currently unavailable. Please try again later.");
	}

	@DeleteMapping("/fallback/book-mgmt")
	public String userMgmtFallback8() throws ServiceUnavailableException {
		throw new ServiceUnavailableException("Book Management Service is currently unavailable. Please try again later.");
	}
}