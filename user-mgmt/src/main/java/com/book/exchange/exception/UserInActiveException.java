package com.book.exchange.exception;

public class UserInActiveException extends RuntimeException {

  public UserInActiveException(String message) {
	  super(message);
  }
}
