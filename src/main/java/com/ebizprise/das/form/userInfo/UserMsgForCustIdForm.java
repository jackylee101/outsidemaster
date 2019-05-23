package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;

public class UserMsgForCustIdForm extends StatusForm {

	private String custId;
	private String mailType;

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
