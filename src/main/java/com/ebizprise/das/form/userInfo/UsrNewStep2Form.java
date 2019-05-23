package com.ebizprise.das.form.userInfo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UsrNewStep2Form {
//	//usrNewStep1
	private String email;
//	private String mobilePhone;
//	private String countryCode;
//	private String usrpwd;
//
//	private String userType;
//	private String saltKey;
//	private String phVerCode;

	private String companyName;
	private String ind01Code;
	private String ind02Code;
	
//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrLevelId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrFungroupId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrEvtgroupId;
	
//	//usrNewStep2
	private String custId;
	private String usrname;
	private String jobTitle;
	private String fileUrl;
	private String isDel;

	private String ip;
	private String captchaCode;
	private String cookieId;
	private String pinCode;

	private String companyId;
	private String icon;

	public String getUsrLevelId() {
		return usrLevelId;
	}
	public void setUsrLevelId(String usrLevelId) {
		this.usrLevelId = usrLevelId;
	}
	public String getUsrFungroupId() {
		return usrFungroupId;
	}
	public void setUsrFungroupId(String usrFungroupId) {
		this.usrFungroupId = usrFungroupId;
	}
	public String getUsrEvtgroupId() {
		return usrEvtgroupId;
	}
	public void setUsrEvtgroupId(String usrEvtgroupId) {
		this.usrEvtgroupId = usrEvtgroupId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getUsrname() {
		return usrname;
	}
	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	public String getCookieId() {
		return cookieId;
	}
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getInd01Code() {
		return ind01Code;
	}
	public void setInd01Code(String ind01Code) {
		this.ind01Code = ind01Code;
	}
	public String getInd02Code() {
		return ind02Code;
	}
	public void setInd02Code(String ind02Code) {
		this.ind02Code = ind02Code;
	}
	
}
