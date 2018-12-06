package com.ebizprise.das.enums;

public enum WebContentType {
	// 01:Level1/02:Level2/03:Level3/04:Level4
	ONE("1"), TWO("2"), THREE("3");

	WebContentType(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}
}
