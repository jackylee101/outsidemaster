package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebizprise.das.web.controller.minor.ForecastParameter;
import com.ebizprise.das.web.controller.minor.PaceBase;

public class PaceDefault12T extends PaceBase {

	private static final Log logger = LogFactory.getLog(PaceDefault12T.class);

	@Autowired
	protected ForecastParameter forecastParameter;

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_2: 查出驗證碼
	 */
	@Test
	public void PaceDefault12T() {

		forecastParameter.captchaCode = "";
		Assert.assertNotNull(forecastParameter.captchaCode);
	}

}
