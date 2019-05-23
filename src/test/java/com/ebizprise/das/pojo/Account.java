package com.ebizprise.das.pojo;

public class Account {
	String custId;
	String email;
	String mobilePhone;
	String usrpwd;
	String usrname;
	String companyName;
	String userType;
	String countryCode;
	String phVerCode;

	String prjDtlId;
	String prjItmId;

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

	public String getUsrpwd() {
		return usrpwd;
	}

	public void setUsrpwd(String usrpwd) {
		this.usrpwd = usrpwd;
	}

	public String getUsrname() {
		return usrname;
	}

	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhVerCode() {
		return phVerCode;
	}

	public void setPhVerCode(String phVerCode) {
		this.phVerCode = phVerCode;
	}

	public String getPrjDtlId() {
		return prjDtlId;
	}

	public void setPrjDtlId(String prjDtlId) {
		this.prjDtlId = prjDtlId;
	}

	public String getPrjItmId() {
		return prjItmId;
	}

	public void setPrjItmId(String prjItmId) {
		this.prjItmId = prjItmId;
	}

	public Object toCsv() {
		String ss = custId + "," + email + "," + mobilePhone + "," + usrpwd
				+ "," + usrname + "," + companyName + "," + userType + ","
				+ countryCode + "," + phVerCode + "," + prjItmId;

		return ss;
	}
}
