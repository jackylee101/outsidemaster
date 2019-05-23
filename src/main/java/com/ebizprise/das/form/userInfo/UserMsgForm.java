package com.ebizprise.das.form.userInfo;

import java.util.List;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserMsgForm extends StatusForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String custId;
	
	private List<UserMsgDetailForm> userMsg;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public List<UserMsgDetailForm> getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(List<UserMsgDetailForm> userMsg) {
		this.userMsg = userMsg;
	}
	
}
