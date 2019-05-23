package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserPredictForm extends StatusForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String custId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int valueSet;
//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	private int d;
//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	private int w;
//	@JsonInclude(JsonInclude.Include.NON_NULL)
//	private int m;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String prjItmId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String prjName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String show;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public int getValueSet() {
		return valueSet;
	}
	public void setValueSet(int valueSet) {
		this.valueSet = valueSet;
	}
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
