package com.ebizprise.das.exception;

public class InsertRowException extends Exception {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsertRowException(String message) {
	        super(message);
	    }

	    public InsertRowException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
