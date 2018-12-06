package com.ebizprise.das.form.system;

public class StatusForm {

	/**
	 * 預設為true
	 */
	public StatusForm() {	
		super();
		this.success = true;
	}

	private Boolean success;

	private String error;
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	 	
}
