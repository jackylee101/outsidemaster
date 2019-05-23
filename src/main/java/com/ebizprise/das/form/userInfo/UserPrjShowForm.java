package com.ebizprise.das.form.userInfo;

import java.util.List;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserPrjShowForm extends StatusForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String custId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<UserPrjShowDetailForm> showSet;
	
	private List<UserPrjShowDetailForm> prjDetail;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public List<UserPrjShowDetailForm> getShowSet() {
		return showSet;
	}
	public void setShowSet(List<UserPrjShowDetailForm> showSet) {
		this.showSet = showSet;
	}
	public List<UserPrjShowDetailForm> getPrjDetail() {
		return prjDetail;
	}
	public void setPrjDetail(List<UserPrjShowDetailForm> prjDetail) {
		this.prjDetail = prjDetail;
	}
	
}
