package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserMsgDetailForm {

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String prjName;
	private String createDate;
	private String preFinDate;
	private String title;
	private String content;
	
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
	public String getPreFinDate() {
		return preFinDate;
	}
	public void setPreFinDate(String preFinDate) {
		this.preFinDate = preFinDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
