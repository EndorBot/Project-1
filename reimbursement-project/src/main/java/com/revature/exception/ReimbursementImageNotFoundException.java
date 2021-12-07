package com.revature.exception;

public class ReimbursementImageNotFoundException extends Exception {

	public ReimbursementImageNotFoundException() {
		super();
	}

	public ReimbursementImageNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementImageNotFoundException(String message) {
		super(message);
	}

	public ReimbursementImageNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
