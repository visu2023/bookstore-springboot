package com.visulabs.bookstore.exception;

public class NotAuthrizedException extends Exception {

	public NotAuthrizedException(String action) {
		super("Not authorized to perform " + action);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
