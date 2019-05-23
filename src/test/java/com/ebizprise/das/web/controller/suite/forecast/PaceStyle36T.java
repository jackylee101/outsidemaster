package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ebizprise.das.form.system.QueryForm;

public class PaceStyle36T extends PaceStyleFactory {

	private static final Log logger = LogFactory.getLog(PaceStyle36T.class);

	/**
	 * 表單自動化測試 -- 全品項預測 Step6:
	 */
	@Test
	public void PaceStyle36T() {

		QueryForm queryForm = paceStyle.pace6(forecastParameter.custId,
				forecastParameter.prjItmId,forecastParameter.prjDtlId);
		String prjItmId = queryForm.getQueryString();
		Assert.assertTrue(queryForm.getSuccess());
	}

}