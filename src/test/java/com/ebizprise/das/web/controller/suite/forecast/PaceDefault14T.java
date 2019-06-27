package com.ebizprise.das.web.controller.suite.forecast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;

public class PaceDefault14T extends PaceDefault {

	private static final Log logger = LogFactory.getLog(PaceDefault14T.class);

	String work = "";

	@Before
	public void setUp() throws Exception {
		int n = (int) (Math.random() * 900) + 100;
		// int i= new java.util.Random().nextInt(900)+100;也可以
		this.work = "test-" + String.valueOf(n);
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_4: 建立專案
	 */
	@Test
	public void PaceDefault14T() {
//		QueryForm queryForm = pace14(FPUtil.custId, work);
//		FPUtil.prjItmId = queryForm.getQueryString();
//		Assert.assertTrue(queryForm.getSuccess());
	}

}
