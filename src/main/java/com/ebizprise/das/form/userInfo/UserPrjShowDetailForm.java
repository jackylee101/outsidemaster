package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserPrjShowDetailForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String prjItmId;
	private String prjName;
	private String createDate;
	private String show;
	
	public String getPrjItmId() {
		return prjItmId;
	}
	public void setPrjItmId(String prjItmId) {
		this.prjItmId = prjItmId;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	
}
