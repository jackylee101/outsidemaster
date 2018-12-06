package com.ebizprise.das.utilsweb.form;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseResponseForm {

	/**
	 * 預設為true
	 */
	public BaseResponseForm() {
		super();
		this.success = true;
	}

	@JSONField(ordinal = 1)
	private Boolean success;

	@JSONField(ordinal = 2)
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
