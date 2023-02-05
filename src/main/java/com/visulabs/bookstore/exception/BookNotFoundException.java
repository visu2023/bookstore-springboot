package com.visulabs.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class BookNotFoundException extends Exception {

	String message;

	public BookNotFoundException(String field, String value) {
		super("Book With  " + field + "= " + value + " not found ");
	}

	public BookNotFoundException(String field) {
		super("Book With  " + field + " not found ");
	}

}
