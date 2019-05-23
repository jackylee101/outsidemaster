package com.ebizprise.das.form.userInfo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserContactForm {
	
	private String custId;
	private String contact;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
