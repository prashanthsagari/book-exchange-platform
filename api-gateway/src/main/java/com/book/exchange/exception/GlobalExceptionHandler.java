package com.book.exchange.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.gateway.support.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> argumentNotValid(ServerWebExchange request,
	        MethodArgumentNotValidException anve) {

		String completeErrorDetails = anve.getFieldErrors().stream()
		        .map(f -> f.getCode() + " : " + f.getField() + " : " + f.getDefaultMessage())
		        .collect(Collectors.joining("  "));

		ErrorMessage errorBody = ErrorMessage.builder().code(anve.getStatusCode().value())
		        .status(HttpStatus.BAD_REQUEST.name()).message(completeErrorDetails).timeStamp(LocalDateTime.now())
		        .path(request.getRequest().getPath().value()).build();

		return ResponseEntity.badRequest().body(errorBody);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> notReadableExcpetion(ServerWebExchange request,
	        HttpMessageNotReadableException nre) {

		ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.BAD_REQUEST.value())
		        .path(request.getRequest().getPath().value()).status(HttpStatus.BAD_REQUEST.name())
		        .message(nre.getMessage()).timeStamp(LocalDateTime.now()).build();

		return ResponseEntity.badRequest().body(errorBody);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorMessage> invalidTokenException(ServerWebExchange request, InvalidTokenException it) {
		ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
		        .status(HttpStatus.INTERNAL_SERVER_ERROR.name()).message(it.getMessage()).timeStamp(LocalDateTime.now())
		        .path(request.getRequest().getPath().value()).build();

		return ResponseEntity.internalServerError().body(errorBody);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupported(ServerWebExchange request,
	        HttpRequestMethodNotSupportedException ex) {
		String message = "HTTP method not supported: " + ex.getMethod() + ". Supported methods are: "
		        + ex.getSupportedHttpMethods();
		ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.METHOD_NOT_ALLOWED.value()).message(message)
		        .status(HttpStatus.METHOD_NOT_ALLOWED.name()).path(request.getRequest().getPath().value())
		        .timeStamp(LocalDateTime.now()).build();
		return new ResponseEntity<ErrorMessage>(errorBody, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<ErrorMessage> handleServiceNotAvailableExceptions(HttpServletRequest request, ServiceUnavailableException se) {
		ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.SERVICE_UNAVAILABLE.value())
		        .message(se.getMessage()).status(HttpStatus.SERVICE_UNAVAILABLE.name()).path(request.getServletPath())
		        .timeStamp(LocalDateTime.now()).build();

		return new ResponseEntity<ErrorMessage>(errorBody, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleAllExceptions(ServerWebExchange request, Exception e) {
		ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
		        .message(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		        .path(request.getRequest().getPath().value()).timeStamp(LocalDateTime.now()).build();

		return new ResponseEntity<ErrorMessage>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}