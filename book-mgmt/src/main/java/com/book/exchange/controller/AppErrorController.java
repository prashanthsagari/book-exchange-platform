package com.book.exchange.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AppErrorController implements ErrorController {
	@GetMapping("/error")
	public String handleError(HttpServletRequest request, HttpServletResponse response) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

		if (statusCode != null) {
			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				return "404";
			} else if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
				return String.format("Unauthorized request %d  %s", statusCode, message);
			} else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				return "500";
			}
		}

		return "/error";
	}
}