package com.ebizprise.das.form.userInfo;

import java.util.List;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class RdsVersionForm extends StatusForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String custId;
	
	private List<RdsVersionDetailForm> rdsVersionInfo;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public List<RdsVersionDetailForm> getRdsVersionInfo() {
		return rdsVersionInfo;
	}
	public void setRdsVersionInfo(List<RdsVersionDetailForm> rdsVersionInfo) {
		this.rdsVersionInfo = rdsVersionInfo;
	}
}
