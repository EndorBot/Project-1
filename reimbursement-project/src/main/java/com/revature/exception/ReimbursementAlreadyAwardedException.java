package com.revature.exception;

public class ReimbursementAlreadyAwardedException extends Exception {

	public ReimbursementAlreadyAwardedException() {
		super();
	}

	public ReimbursementAlreadyAwardedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementAlreadyAwardedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementAlreadyAwardedException(String message) {
		super(message);
	}

	public ReimbursementAlreadyAwardedException(Throwable cause) {
		super(cause);
	}
	
}
