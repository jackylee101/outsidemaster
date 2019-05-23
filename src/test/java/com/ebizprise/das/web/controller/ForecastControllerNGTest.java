package com.ebizprise.das.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.ebizprise.das.RootNGTest;
import com.ebizprise.das.form.base.DvdForm2;
import com.ebizprise.das.form.base.PriceForm2;
import com.ebizprise.das.form.base.PriveAuthForm;
import com.ebizprise.das.form.base.ProductForm;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;
import com.ebizprise.das.web.controller.minor.PaceStyle;
import com.ebizprise.das.web.controller.suite.forecast.PaceStyleFactory;
import com.prive.das.utils.ExcelPOIXml;

/**
 * 單品項預測 大單元測試
 * 
 * @author jacky.lee
 *
 */
//@ActiveProfiles("dev2")
public class ForecastControllerNGTest extends RootNGTest {

	private static final Log logger = LogFactory.getLog(ForecastControllerNGTest.class);

	@LocalServerPort
	protected int port;

	protected String baseUrl;

	@Autowired
	private PaceDefault paceDefault;

	@Autowired
	private PaceStyleFactory paceStyleFactory;

	public ForecastControllerNGTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		this.baseUrl = "localhost";
	}

	// private String custId = "000093";
	// private String prjItmId = "000093000458";
	// private String prjDtlId;

	private static Integer bo = new Integer(0);

	protected synchronized static Integer getWhich() // 同步的static 函數
	{
		return bo++;
	}

	protected static String[][] user1 = { { "prive_admin", "j@8u&SQw1!" }, { "irene_6.su@ebizprise.com", "1234" } };

//	@Test(threadPoolSize = 1, invocationCount = 1, timeOut = 5000000)
//	public void pace0() {
//
//		// FPUtil.realHost = "http://192.168.128.23:8080";
//		FPUtil.realHost = "http://localhost:8075";
//		// FPUtil.realHost = "http://uatrds.rollingdemand.com.cn";
//		try {
//			Thread.sleep((int) (Math.random() * 3000));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		int n = getWhich();
//		logger.warn(n);
//		pace1(user1[n][0], user1[n][1]);
//	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * TEST2.xlsx 屬於 Type1型的預測資料檔
	 */
	public void paceE0T(String username, String password) {
//		PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
//		username = "jacky.lee@ebizprise.com";
//		username = "pkpkpk";
		String path = "/tmp/E0T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0005042456");
		schemaList.add("IE00B53QG562");
		schemaList.add("IE00B2QWCY14");
		schemaList.add("IE00B5BMR087");
		schemaList.add("LU1781541252");
		schemaList.add("IE00B52MJY50");
		schemaList.add("IE00BKM4GZ66");
		schemaList.add("IE00B52SF786");
		schemaList.add("IE00BSKRJZ44");
		schemaList.add("IE00BZ163M45");
		schemaList.add("IE00B2NPKV68");
		schemaList.add("LU1452600270");
		schemaList.add("IE00BCRY6227");
		schemaList.add("IE00BCRY5Y77");
		schemaList.add("IE00B2QWDY88");
		schemaList.add("IE0032077012");
		schemaList.add("LU1407889887");

		pace9_1(username, password, schemaList, path);
//		pace0_3(paceStyle);
	}

	public void paceE1T(String username, String password) {
//		PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
//		username = "jacky.lee@ebizprise.com";
//		username = "pkpkpk";
		String path = "/tmp/E1T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0005042456");
		schemaList.add("IE00B53QG562");
		schemaList.add("IE00B2QWCY14");
		schemaList.add("IE00B5BMR087");
		schemaList.add("LU1781541252");
		schemaList.add("IE00B52MJY50");
		schemaList.add("IE00BKM4GZ66");
		schemaList.add("IE00B52SF786");
		schemaList.add("IE00BSKRJZ44");
		schemaList.add("IE00BZ163M45");
		schemaList.add("IE00B2NPKV68");
		schemaList.add("LU1452600270");
		schemaList.add("IE00BCRY6227");
		schemaList.add("IE00BCRY5Y77");

		pace9_1(username, password, schemaList, path);
//		pace0_3(paceStyle);
	}

	public void paceE2T(String username, String password) {
		String path = "/tmp/E2T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0005042456");
		schemaList.add("IE00B53QG562");
		schemaList.add("IE00B2QWCY14");
		schemaList.add("IE00B5BMR087");
		schemaList.add("IE00B2QWDY88");
		schemaList.add("LU1781541252");
		schemaList.add("IE00B52MJY50");
		schemaList.add("IE00BKM4GZ66");
		schemaList.add("IE00B52SF786");
		schemaList.add("IE00BSKRJZ44");
		schemaList.add("IE00BZ163M45");
		schemaList.add("IE00B2NPKV68");
		schemaList.add("LU1452600270");
		schemaList.add("IE00BCRY6227");
		schemaList.add("IE00BCRY5Y77");

		pace9_1(username, password, schemaList, path);
	}

	public void paceE3T(String username, String password) {
		String path = "/tmp/E3T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0005042456");
		schemaList.add("IE00B53QG562");
		schemaList.add("IE00B2QWCY14");
		schemaList.add("IE00B5BMR087");
		schemaList.add("IE0032077012");
		schemaList.add("IE00B2QWDY88");
		schemaList.add("LU1781541252");
		schemaList.add("IE00B52MJY50");
		schemaList.add("IE00BKM4GZ66");
		schemaList.add("IE00B52SF786");
		schemaList.add("LU1407889887");
		schemaList.add("IE00BSKRJZ44");
		schemaList.add("IE00BZ163M45");
		schemaList.add("IE00B2NPKV68");
		schemaList.add("LU1452600270");
		schemaList.add("IE00BCRY6227");
		schemaList.add("IE00BCRY5Y77");

		pace9_1(username, password, schemaList, path);
	}

	public void paceE4T(String username, String password) {
		String path = "/tmp/E4T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0005042456");
		schemaList.add("IE00B53QG562");
		schemaList.add("IE00B2QWCY14");
		schemaList.add("IE00B5BMR087");
		schemaList.add("IE0032077012");
		schemaList.add("IE00B2QWDY88");
		schemaList.add("LU1781541252");
		schemaList.add("IE00B52MJY50");
		schemaList.add("IE00BKM4GZ66");
		schemaList.add("IE00B52SF786");
		schemaList.add("LU1407889887");
		schemaList.add("IE00BSKRJZ44");
		schemaList.add("IE00B2NPKV68");
		schemaList.add("IE00BCRY5Y77");
		
		pace9_1(username, password, schemaList, path);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * POC_GREEN2.xlsx 屬於 Type2型的預測資料檔
	 */
	public void pace2(String email, String usrpwd) {
		logger.warn("pace2");
		// String filePath =
		// "/jsp/das_backend/web/src/test/java/com/ebizprise/das/web/controller/POC_GREEN2.xlsx";
		// PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		String filePath = "/POC_GREEN2.xls";
		PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * TEST3.xlsx 屬於 Type3型的預測資料檔
	 */
	public void pace3(String email, String usrpwd) {
		logger.warn("pace3 " + email);
		String filePath = "/TEST3.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("TEST3");
		// email = "jacky.lee@ebizprise.com";
		// usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * b19.xlsx 屬於 Type4型的預測資料檔
	 */
	public void pace4(String email, String usrpwd) {
		logger.warn("pace4");
		// String filePath =
		// "/jsp/das_backend/web/src/test/java/com/ebizprise/das/web/controller/POC_GREEN2.xlsx";
		// PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		String filePath = "/pig_b19.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("b19");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * b19.xlsx 屬於 Type5型的預測資料檔
	 */
	public void pace5(String email, String usrpwd) {
		logger.warn("pace5");
		// String filePath =
		// "/jsp/das_backend/web/src/test/java/com/ebizprise/das/web/controller/POC_GREEN2.xlsx";
		// PaceStyle paceStyle = paceStyleFactory.getStyle("POC_GREEN2");
		String filePath = "/pig_b19_1.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("pig_b19");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * trainSalesForecastKaggle20190218_1.csv 屬於 Type6型的預測資料檔
	 */
	public void pace6(String email, String usrpwd) {
		logger.warn("pace6");
		String filePath = "/trainSalesForecastKaggle20190218_1.csv";
		PaceStyle paceStyle = paceStyleFactory.getStyle("trainSalesForecastKaggle20190218");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * trainSalesForecastKaggle20190218_1.csv 屬於 Type6型的預測資料檔
	 */
	public void pace7(String email, String usrpwd) {
		logger.warn("pace7");
		String filePath = "/wechun_date_iso.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("wechun_date_iso");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	public void pace8(String email, String usrpwd) {
		logger.warn("pace8");
		String filePath = "/RDS_SampleData.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("RDS_SampleData");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	public void pace9(String email, String usrpwd) {
		logger.warn("pace9");
		String filePath = "/order_calc.xlsx";
		PaceStyle paceStyle = paceStyleFactory.getStyle("order_calc");
		email = "jacky.lee@ebizprise.com";
		usrpwd = "pkpkpk";
		pace0_1(email, usrpwd, filePath);
		pace0_3(paceStyle);
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_1: 共同部份,每個交易前5步驟都會一樣
	 */
	private void pace9_1(String username, String password, List<String> schemaList, String path) {
		// Class[] argType11 = {};
		// Object[] args11 = {};
		// DasCaptchForm dasCaptchForm1 = (DasCaptchForm) paceDefault.pace(
		// "pace11", argType11, args11);

		PriveAuthForm priveAuthForm = paceDefault.pace11(username, password);
		String access_token = priveAuthForm.getAccess_token();
		String token_type = priveAuthForm.getToken_type();
		Assert.assertNotNull(token_type);
		Assert.assertNotNull(access_token);

		List queryList = new ArrayList();
		for (int i = 0; i < schemaList.size(); i++) {
			String schema = schemaList.get(i);
			Map mp1 = new HashMap();
			mp1.put("scheme", "ISIN");
			mp1.put("value", schema);
			mp1.put("currency", "USD");
			queryList.add(mp1);
		}
		String token = token_type + " " + access_token;

		List<ProductForm> productFormList = paceDefault.pace12(token, queryList);
		List lines = new ArrayList();

		ExcelPOIXml ep = new ExcelPOIXml();
		ep.outExcel(lines, path);
		String sheetName1 = "Sheet 1";
		String sheetName2 = "Sheet 2";
		String sheetName3 = "Sheet 3";
		ep.outExcelposition("Date", sheetName1, 0, 0, path);
		ep.outExcelposition("Date", sheetName2, 0, 0, path);
		for (int i = 0; i < productFormList.size(); i++) {
			ProductForm productForm = productFormList.get(i);
			String assetId = productForm.getAssetId();
			String from = "2019-05-16";
			String to = "2019-05-23";
			List priceList = paceDefault.pace13(token, assetId, from, to);
			String schema = schemaList.get(i);
			ep.outExcelposition(schema, sheetName1, 0, i + 1, path);
			ep.outExcelposition(schema, sheetName2, 0, i * 2 + 1, path);
			logger.info(schema);
			showPrice(priceList, ep, sheetName1, path, i + 1, sheetName2);

			List dvdList = paceDefault.pace14(token, assetId, "2019-05-14", "2019-05-21");
			ep.outExcelposition(schema, sheetName3, 0, i + 1, path);
//			ep.outExcelposition(schema, sheetName2, 0, i * 2 + 1, path);
			showDvd(dvdList, ep, sheetName3, path, i + 1);
		}

	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_1: 共同部份,每個交易前5步驟都會一樣
	 */
	private void pace0_1(String username, String password, String path) {
//		PriveAuthForm priveAuthForm = paceDefault.pace11(username, password);
//		String access_token = priveAuthForm.getAccess_token();
//		String token_type = priveAuthForm.getToken_type();
//		Assert.assertNotNull(token_type);
//		Assert.assertNotNull(access_token);
//
//		List<String> schemaList = new ArrayList();
//		schemaList.add("IE0005042456");
//		schemaList.add("IE00B53QG562");
//		schemaList.add("IE00B2QWCY14");
//		schemaList.add("IE00B5BMR087");
//		schemaList.add("LU1781541252");
//		schemaList.add("IE00B52MJY50");
//		schemaList.add("IE00BKM4GZ66");
//		schemaList.add("IE00B52SF786");
//		schemaList.add("IE00BSKRJZ44");
//		schemaList.add("IE00BZ163M45");
//		schemaList.add("IE00B2NPKV68");
//		schemaList.add("LU1452600270");
//		schemaList.add("IE00BCRY6227");
//		schemaList.add("IE00BCRY5Y77");
//		schemaList.add("IE00B2QWDY88");
//		schemaList.add("IE0032077012");
//		schemaList.add("LU1407889887");
//
//		List queryList = new ArrayList();
//		for (int i = 0; i < schemaList.size(); i++) {
//			String schema = schemaList.get(i);
//			Map mp1 = new HashMap();
//			mp1.put("scheme", "ISIN");
//			mp1.put("value", schema);
//			mp1.put("currency", "USD");
//			queryList.add(mp1);
//		}
//		String token = token_type + " " + access_token;
//
//		List<ProductForm> productFormList = paceDefault.pace12(token, queryList);
//		List lines = new ArrayList();
//
//		ExcelPOIXml ep = new ExcelPOIXml();
//		ep.outExcel(lines, path);
//		String sheetName = "Sheet 1";
//		String sheetName2 = "Sheet 2";
//		ep.outExcelposition("Date", sheetName, 0, 0, path);
//		ep.outExcelposition("Date", sheetName2, 0, 0, path);
//		for (int i = 0; i < productFormList.size(); i++) {
//			ProductForm productForm = productFormList.get(i);
//			String assetId = productForm.getAssetId();
//			String from = "2019-05-01";
//			String to = "2019-05-13";
//			List priceList = paceDefault.pace13(token, assetId, from, to);
//			String schema = schemaList.get(i);
//			ep.outExcelposition(schema, sheetName, 0, i + 1, path);
//			ep.outExcelposition(schema, sheetName2, 0, i * 2 + 1, path);
//			logger.info(schema);
//			showPrice(priceList, ep, sheetName, path, i + 1, sheetName2);
//		}

	}

	private void showPrice(List priceList, ExcelPOIXml ep, String sheetName, String path, int field,
			String sheetName2) {
		for (int i = 0; i < priceList.size(); i++) {
			PriceForm2 priceForm = (PriceForm2) priceList.get(i);
			logger.info(priceForm.getLocalDate() + " : " + priceForm.getClosePx());
			ep.outExcelposition(priceForm.getClosePx(), sheetName, i + 1, field, path);
			ep.outExcelposition(priceForm.getLocalDate(), sheetName, i + 1, 0, path);

			ep.outExcelposition(priceForm.getClosePx(), sheetName2, i + 1, field * 2 - 1, path);
			ep.outExcelposition(priceForm.getLocalDate(), sheetName2, i + 1, 0, path);
		}
	}

	private void showDvd(List dvdList, ExcelPOIXml ep, String sheetName3, String path, int field) {
		for (int i = 0; i < dvdList.size(); i++) {
			DvdForm2 dvdForm2 = (DvdForm2) dvdList.get(i);
			logger.info(dvdForm2.getExDate() + " : " + dvdForm2.getValue());
			ep.outExcelposition(dvdForm2.getValue(), sheetName3, i + 1, field, path);
			ep.outExcelposition(dvdForm2.getExDate(), sheetName3, i + 1, 0, path);
		}
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_3: 後面3~10步驟根據上傳檔案格式有所不同
	 */
	private void pace0_3(PaceStyle paceStyle) {
		String custId = FPUtil.custId;
		String prjItmId = FPUtil.prjItmId;
		String prjDtlId = FPUtil.prjDtlId;

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

}
