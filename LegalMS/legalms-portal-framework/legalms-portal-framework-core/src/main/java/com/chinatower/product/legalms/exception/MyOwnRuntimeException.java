package com.chinatower.product.legalms.exception;

public class MyOwnRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyOwnRuntimeException() {
		super();
	}

	public MyOwnRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MyOwnRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyOwnRuntimeException(String message) {
		super(message);
	}

	public MyOwnRuntimeException(Throwable cause) {
		super(cause);
	}

}
