package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class RdsVersionDetailForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String versionNo;
	private String depDate;
	private String content;
	
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getDepDate() {
		return depDate;
	}
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
