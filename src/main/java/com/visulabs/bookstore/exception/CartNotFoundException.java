package com.visulabs.bookstore.exception;

public class CartNotFoundException extends Exception {

	String message;

	public CartNotFoundException(String s) {
		this.message = s;
	}

	public String getMessage() {
		return message;
	}

}
