package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ebizprise.das.form.userInfo.UserInfoCheckForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;

public class PaceDefault13T extends PaceDefault {

	private static final Log logger = LogFactory.getLog(PaceDefault13T.class);

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_3: 登入
	 */
	@Test
	public void PaceDefault13T() {

		UserInfoCheckForm userInfoCheckForm = pace13(FPUtil.email,
				FPUtil.usrpwd, FPUtil.captchaCode, FPUtil.cookieId);
		FPUtil.custId = userInfoCheckForm.getCustId();
		Assert.assertNotNull(FPUtil.custId);
	}

}
