package com.book.exchange.exception;

public class PasswordNotMatchedException extends RuntimeException {

	public PasswordNotMatchedException(String message) {
		super(message);
	}
}
