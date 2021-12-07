package com.revature.exception;

public class ReimbursementAlreadyGradedException extends Exception {

	public ReimbursementAlreadyGradedException() {
		super();
	}

	public ReimbursementAlreadyGradedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementAlreadyGradedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementAlreadyGradedException(String message) {
		super(message);
	}

	public ReimbursementAlreadyGradedException(Throwable cause) {
		super(cause);
	}
	
}
