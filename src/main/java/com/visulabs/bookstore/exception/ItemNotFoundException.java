package com.visulabs.bookstore.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ItemNotFoundException extends Exception {

	String message;

	public ItemNotFoundException() {
		super("Book  not found ");
	}

}
