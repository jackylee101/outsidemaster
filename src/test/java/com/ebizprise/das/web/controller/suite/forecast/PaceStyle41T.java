package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ebizprise.das.form.system.QueryForm;

public class PaceStyle41T extends PaceStyleFactory {

	private static final Log logger = LogFactory.getLog(PaceStyle41T.class);

	/**
	 * 表單自動化測試 -- 全品項預測 Step11-- 檢查預測完成:
	 */
	@Test
	public void PaceStyle41T() {

		QueryForm queryForm = paceStyle.pace11(forecastParameter.custId,
				forecastParameter.prjItmId, forecastParameter.prjDtlId);
		String costTime = queryForm.getQueryString();
		Assert.assertTrue(queryForm.getSuccess());
	}

}