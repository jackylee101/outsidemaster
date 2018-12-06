package com.ebizprise.das.model;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;
	private Date version;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

}
