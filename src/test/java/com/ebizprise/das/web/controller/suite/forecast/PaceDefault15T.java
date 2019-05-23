package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;

public class PaceDefault15T extends PaceDefault {

	private static final Log logger = LogFactory.getLog(PaceDefault15T.class);

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_5: 上傳預測資料檔案
	 */
	@Test
	public void PaceDefault15T() {

		QueryForm queryForm = pace15(FPUtil.filePath, FPUtil.custId,
				FPUtil.prjItmId);
		FPUtil.prjDtlId = queryForm.getQueryString();
		Assert.assertTrue(queryForm.getSuccess());
	}

}
