package com.kits.exceptionhandler;

public class AggregateServiceException extends Exception {

	private String message;

	public AggregateServiceException(String message) {
		super(message);
		this.message = message;
	}

	public AggregateServiceException() {
	}
}
