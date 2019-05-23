package com.ebizprise.das.form.userInfo;

public class UserPwdResetForm {

	private String custId;
	private String email;
	private String mobilePhone;
	private String usrNewpwd;
	private String captchaCode;
	private String cookieId;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getUsrNewpwd() {
		return usrNewpwd;
	}
	public void setUsrNewpwd(String usrNewpwd) {
		this.usrNewpwd = usrNewpwd;
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
	
}
