package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ebizprise.das.form.base.DasCaptchForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;

public class PaceDefault11T extends PaceDefault {

	private static final Log logger = LogFactory.getLog(PaceDefault11T.class);

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_1: 先產生驗證碼
	 */
	@Test
	public void PaceDefault11T() {

		DasCaptchForm dasCaptchForm = pace11();
		FPUtil.cookieId = dasCaptchForm.getCookieId();
		Assert.assertNotNull(FPUtil.cookieId);
	}

}
