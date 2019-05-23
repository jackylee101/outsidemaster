package com.ebizprise.das.web.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.Assert;

//import com.ebizprise.das.form.base.DasCaptchForm;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.form.userInfo.UserInfoCheckForm;
import com.ebizprise.das.pojo.Account;
import com.ebizprise.das.web.controller.minor.PaceDefault;
import com.ebizprise.das.web.controller.minor.PaceStyle;
import com.ebizprise.das.web.controller.suite.forecast.PaceStyleFactory;

/**
 * 單品項預測 大單元測試
 * 
 * @author jacky.lee
 *
 */
@Service
public class ForecastServiceImpl {

	private static final Log logger = LogFactory
			.getLog(ForecastServiceImpl.class);

	protected String baseUrl;

	@Autowired
	private PaceDefault paceDefault;

	@Autowired
	private PaceStyleFactory paceStyleFactory;

	private static Integer bo = new Integer(0);

	private synchronized static Integer getWhich() // 同步的static 函數
	{
		return bo++;
	}

	//
	// @Test(threadPoolSize = 1, invocationCount = 1, timeOut = 5000000)
	// public void pace0() {
	// FPUtil.realHost = "http://192.168.128.23:8080";
	// try {
	// Thread.sleep((int) (Math.random() * 3000));
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// int n = getWhich();
	// logger.error(n);
	// pace1(user1[n][0], user1[n][1]);
	// }

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * TEST2.xlsx 屬於 Type1型的預測資料檔
	 */
	// @Test
	public void pace1(Account account) {
		String filePath = "/TEST1.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
		pace0_1(account, filePath);
		pace0_3(account, paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * POC_GREEN2.xlsx 屬於 Type2型的預測資料檔
	 */
	public void pace2(Account account) {
		logger.error("pace2");
		// String filePath =
		// "/jsp/das_backend/web/src/test/java/com/ebizprise/das/web/controller/POC_GREEN2.xlsx";
		// PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		String filePath = "/POC_GREEN2.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		account.setEmail("jacky.lee@ebizprise.com");
		account.setUsrpwd("pkpkpk");
		pace0_1(account, filePath);
		pace0_3(account, paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * TEST3.xlsx 屬於 Type3型的預測資料檔
	 */
	public void pace3(Account account) {
		logger.error("pace3 " + account.getEmail());
		String filePath = "/TEST3.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("TEST3");
		// email = "jacky.lee@ebizprise.com";
		// usrpwd = "pkpkpk";
		pace0_1(account, filePath);
		pace0_3(account, paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_1: 共同部份,每個交易前5步驟都會一樣
	 */
	private void pace0_1(Account account, String filePath) {
		// Class[] argType11 = {};
		// Object[] args11 = {};
		// DasCaptchForm dasCaptchForm1 = (DasCaptchForm) paceDefault.pace(
		// "pace11", argType11, args11);

//		DasCaptchForm dasCaptchForm = paceDefault.pace11();
//		String cookieId = dasCaptchForm.getCookieId();
//		Assert.assertNotNull(cookieId);
//
//		String captchaCode = pace12(cookieId);
//		Assert.assertNotNull(captchaCode);
//
//		UserInfoCheckForm userInfoCheckForm = paceDefault.pace13(
//				account.getEmail(), account.getUsrpwd(), captchaCode, cookieId);
//		String custId = userInfoCheckForm.getCustId();
//		Assert.assertNotNull(custId);
//		account.setCustId(custId);
//		int n = (int) (Math.random() * 900) + 100;
//		// int i= new java.util.Random().nextInt(900)+100;也可以
//
//		String work = "test-" + String.valueOf(n);
//		QueryForm queryForm = paceDefault.pace14(custId, work);
//		String prjItmId = queryForm.getQueryString();
//		Assert.assertTrue(queryForm.getSuccess());
//
//		QueryForm queryForm2 = paceDefault.pace15(filePath, custId, prjItmId);
//		String prjDtlId = queryForm2.getQueryString();
//		Assert.assertTrue(queryForm2.getSuccess());
//		account.setPrjDtlId(prjDtlId);
//		account.setPrjItmId(prjItmId);
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_3: 後面3~10步驟根據上傳檔案格式有所不同
	 */
	private void pace0_3(Account account, PaceStyle paceStyle) {
		String custId = account.getCustId();
		String prjItmId = account.getPrjItmId();
		String prjDtlId = account.getPrjDtlId();

		QueryForm queryForm3 = paceStyle.pace3(custId, prjItmId, prjDtlId);
		String prjItmId3 = queryForm3.getQueryString();
		Assert.assertTrue(queryForm3.getSuccess());

		QueryForm queryForm4 = paceStyle.pace4(custId, prjItmId, prjDtlId);
		String prjItmId4 = queryForm4.getQueryString();
		Assert.assertTrue(queryForm4.getSuccess());

		QueryForm queryForm5 = paceStyle.pace5(custId, prjItmId, prjDtlId);
		String prjItmId5 = queryForm5.getQueryString();
		Assert.assertTrue(queryForm5.getSuccess());

		QueryForm queryForm6 = paceStyle.pace6(custId, prjItmId, prjDtlId);
		String prjItmId6 = queryForm6.getQueryString();
		Assert.assertTrue(queryForm6.getSuccess());

		QueryForm queryForm7 = paceStyle.pace7(custId, prjItmId, prjDtlId);
		String prjItmId7 = queryForm7.getQueryString();
		Assert.assertTrue(queryForm7.getSuccess());

		QueryForm queryForm8 = paceStyle.pace8(custId, prjItmId, prjDtlId);
		String prjItmId8 = queryForm8.getQueryString();
		Assert.assertTrue(queryForm8.getSuccess());

		QueryForm queryForm9 = paceStyle.pace9(custId, prjItmId, prjDtlId);
		String prjItmId9 = queryForm9.getQueryString();
		Assert.assertTrue(queryForm9.getSuccess());

		// 最後一步,之後就會直連投預測
		QueryForm queryForm10 = paceStyle.pace10(custId, prjItmId, prjDtlId);
		String prjItmId10 = queryForm10.getQueryString();
		Assert.assertTrue(queryForm10.getSuccess());

		// 等待預設結束
		QueryForm queryForm11 = paceStyle.pace11(custId, prjItmId, prjDtlId);
		String prjItmId11 = queryForm11.getQueryString();
		Assert.assertTrue(queryForm11.getSuccess());
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_2: 查出驗證碼
	 */
	protected String pace12(String cookieId) {
		String captchaCode = "";
		return captchaCode;
	}

}
