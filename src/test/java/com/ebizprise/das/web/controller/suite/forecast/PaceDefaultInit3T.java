package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;

public class PaceDefaultInit3T extends PaceDefault {

	private static final Log logger = LogFactory
			.getLog(PaceDefaultInit1T.class);

	@Before
	public void setUp() throws Exception {
		FPUtil.email = "jacky.lee@ebizprise.com";
		FPUtil.usrpwd = "pkpkpk";
		FPUtil.filePath = "/TEST3.xlsx";
		FPUtil.style = "TEST3";
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_1: 先產生驗證碼
	 */
	@Test
	public void PaceDefaultInit1T() {
		Assert.assertTrue(true);
	}

}
