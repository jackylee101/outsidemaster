package com.ebizprise.das.enums;

public enum APIStatusCode {
	START("START"), 
	STOP("STOP"),
	END("END"),
	;
	
	APIStatusCode(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}
}
