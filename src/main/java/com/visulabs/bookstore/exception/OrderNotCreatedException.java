package com.visulabs.bookstore.exception;

public class OrderNotCreatedException extends Exception {

	String message;

	public OrderNotCreatedException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return this.message;
	}

}
