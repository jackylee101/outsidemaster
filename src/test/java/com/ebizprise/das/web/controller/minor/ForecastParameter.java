package com.ebizprise.das.web.controller.minor;

import org.springframework.stereotype.Component;

@Component
public class ForecastParameter extends PaceBase {

	public String cookieId;
	public String captchaCode;
	public String custId;
	public String prjItmId;
	public String prjDtlId;
	public String style;
	public String filePath;
	public String email;
	public String usrpwd;

	// @Before
	// public void setUp() throws Exception {
	// this.filePath = "\\mnt\\vdb1\\das\\output\\jmx\\TEST2.xlsx";
	// this.style = "TEST2";
	// }
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPrjItmId() {
		return prjItmId;
	}

	public void setPrjItmId(String prjItmId) {
		this.prjItmId = prjItmId;
	}

	public String getPrjDtlId() {
		return prjDtlId;
	}

	public void setPrjDtlId(String prjDtlId) {
		this.prjDtlId = prjDtlId;
	}

}
