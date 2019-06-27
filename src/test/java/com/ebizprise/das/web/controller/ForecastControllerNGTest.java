package com.ebizprise.das.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.thymeleaf.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebizprise.das.RootNGTest;
import com.ebizprise.das.form.base.DvdForm2;
import com.ebizprise.das.form.base.PriceForm2;
import com.ebizprise.das.form.base.PriveAuthForm;
import com.ebizprise.das.form.base.ProductForm;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.pojo.Benchmark;
import com.ebizprise.das.pojo.ModelPortfolio;
import com.ebizprise.das.pojo.Portfolio;
import com.ebizprise.das.utils.AccessFile;
import com.ebizprise.das.utils.DateUtil;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceDefault;
import com.ebizprise.das.web.controller.minor.PaceStyle;
import com.ebizprise.das.web.controller.suite.forecast.PaceStyleFactory;
import com.prive.das.utils.ExcelPOIXml;

import bsh.StringUtil;

/**
 * 單品項預測 大單元測試
 * 
 * @author jacky.lee
 *
 */
// @ActiveProfiles("dev2")
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

	// @Test(threadPoolSize = 1, invocationCount = 1, timeOut = 5000000)
	// public void pace0() {
	//
	// // FPUtil.realHost = "http://192.168.128.23:8080";
	// FPUtil.realHost = "http://localhost:8075";
	// // FPUtil.realHost = "http://uatrds.rollingdemand.com.cn";
	// try {
	// Thread.sleep((int) (Math.random() * 3000));
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// int n = getWhich();
	// logger.warn(n);
	// pace1(user1[n][0], user1[n][1]);
	// }

	public void paceE0A(String username, String password,String targetDate) {
		String path = "/tmp/Fubon Nano#1 ETF NAV Calculator (for 20190529 reblance) Jacky.xlsx";
		ExcelPOIXml ep = new ExcelPOIXml();
		XSSFWorkbook workbook = ep.loadExcel(path);
		String sheetName = "MPs";
		List list = ep.readExcel(workbook, sheetName);
//		String targetDate = "2019-06-25";

		ModelPortfolio E1T = prepareModel(targetDate, "E1T", list, 1, 5, 20);
		ModelPortfolio E2T = prepareModel(targetDate, "E2T", list, 23, 26, 42);
		ModelPortfolio E3T = prepareModel(targetDate, "E3T", list, 45, 48, 67);
		ModelPortfolio E4T = prepareModel(targetDate, "E4T", list, 70, 73, 88);

		pace9_2(username, password, targetDate, E1T, path);
		pace9_2(username, password, targetDate, E2T, path);
		pace9_2(username, password, targetDate, E3T, path);
		pace9_2(username, password, targetDate, E4T, path);

		showCurrencyNav(E1T);
		showCurrencyNav(E2T);
		showCurrencyNav(E3T);
		showCurrencyNav(E4T);
	}

	protected void paceE0S(String csv) {
		String filename = "/tmp/" + csv + ".csv";
		List listBenchmark = new ArrayList();
		Benchmark benz = null;
		List list = AccessFile.ReadFile_to_List(filename);
		for (int i = 0; i < list.size(); i++) {
			String ss = (String) list.get(i);
			String[] sa = StringUtil.split(ss, ",");
			if ("null".equals(sa[0])) {
				if (benz != null) {
					listBenchmark.add(benz);
				}
				logger.info(sa[1]);
				benz = new Benchmark(sa[1]);
			} else
				benz.addPortfolio(new Portfolio(sa[0], sa[1]));
		}
		String path1 = "/tmp/bloomberg_PRICE_ALL.xlsx";
		String path2 = "/tmp/bloomberg_PRICE.xlsx";
		File f1 = new File(path1);
		if (f1.exists())
			f1.delete();
		File f2 = new File(path2);
		if (f2.exists())
			f2.delete();

		ExcelPOIXml ep = new ExcelPOIXml();

		for (int i = 0; i < listBenchmark.size(); i++) {
			Benchmark benw = (Benchmark) listBenchmark.get(i);
			showBenchmarkAll(ep, path1, benw);
			showBenchmark(ep, path2, benw);
		}

	}

	private void showBenchmarkAll(ExcelPOIXml ep, String path, Benchmark benw) {
		String sheetName = benw.getName();
		logger.info("create sheet: " + sheetName);
		List lines = new ArrayList();
		List<Portfolio> listPortfolio = benw.getPortfolioList();
		for (int i = 0; i < listPortfolio.size(); i++) {
			Portfolio portfolio = listPortfolio.get(i);
			List ay = new ArrayList();
			ay.add(portfolio.getLocalDate());
			ay.add(portfolio.getPrice().toString());
			lines.add(ay);
		}
		ep.outExcel(sheetName, lines, path);
	}

	private void showBenchmark(ExcelPOIXml ep, String path, Benchmark benw) {
		String sheetName = benw.getName();
		logger.info("create sheet: " + sheetName);
		List lines = new ArrayList();
		List<Portfolio> listPortfolio = benw.getPortfolioList();

		String from = "1999-05-01";
		String to = "2019-05-17";
		List portfolioList = new ArrayList();
		Date dFrom = DateUtil.str2Date(from, "yyyy-MM-dd");
		Date dTo = DateUtil.str2Date(to, "yyyy-MM-dd");
		for (Date dIdx = dFrom; DateUtil.LessEqual(dIdx, dTo); dIdx = DateUtil.add(dIdx, 1)) {
			String sTest = DateUtil.date2str(dIdx, "yyyyMMdd");
//			if ("20160101".equals(sTest))
//				logger.debug("why");
			Portfolio portfolio = paceDefault.queryPortfolio(listPortfolio, dIdx);
			if (portfolio != null)
				portfolioList.add(portfolio);
		}

		String oDate = "";
		for (int i = 0; i < portfolioList.size(); i++) {
			Portfolio portfolio = (Portfolio) portfolioList.get(i);
			String sDate = portfolio.getLocalDate();
//			if ("20160101".equals(sDate))
//				logger.debug("why");
			Date dDate = DateUtil.str2Date(sDate, "yyyyMMdd");
			if ((sDate.endsWith("1231") || sDate.endsWith("0101")) && !oDate.equals(sDate)) {
				List ay = new ArrayList();
				String dOut = DateUtil.date2str(dDate, "MM/dd/yyyy");
				ay.add(dOut);
				ay.add(portfolio.getPrice().toString());
				lines.add(ay);
				oDate = sDate;
			}
		}
		ep.outExcel(sheetName, lines, path);
	}

	private ModelPortfolio prepareModel(String targetDate, String modelName, List list, int navRow, int startrow,
			int endrow) {
		List list1 = (List) list.get(navRow);
		String navS = (String) list1.get(1);
		BigDecimal nav = new BigDecimal(navS);
		ModelPortfolio mp = new ModelPortfolio(targetDate, modelName, nav);
		prepareModelPortfolio(mp, list, startrow, endrow);
		verify(mp);
		return mp;
	}

	private void prepareModelPortfolio(ModelPortfolio mp, List list, int startrow, int endrow) {

		for (int i = startrow; i <= endrow; i++) {
			List list1 = (List) list.get(i);
			String name = (String) list1.get(0);
			String isin = (String) list1.get(1);
			String ccy = (String) list1.get(2);
			String weightS = (String) list1.get(3);
			String priceS = (String) list1.get(5);
			String unitS = (String) list1.get(6);

			BigDecimal weight = new BigDecimal(weightS);
			BigDecimal price = new BigDecimal(priceS);
			BigDecimal unit = new BigDecimal(unitS);

			BigDecimal fixunit = verify(mp.getNav(), weight, price, unit);

			Portfolio po = new Portfolio(name, isin, ccy, weight, price, fixunit);
			mp.addPortfolio(po);
		}

	}

	private void verify(ModelPortfolio mp) {
		List<Portfolio> portfolioList = mp.getPortfolioList();
		BigDecimal allWeight = new BigDecimal(0);
		for (int i = 0; i < portfolioList.size(); i++) {
			Portfolio po = portfolioList.get(i);
			allWeight = allWeight.add(po.getWeight());
		}
		int ret3 = allWeight.compareTo(new BigDecimal(1));
		if (ret3 != 0)
			logger.error("weight有誤");
	}

	private BigDecimal verify(BigDecimal nav, BigDecimal weight, BigDecimal price, BigDecimal unit) {
		BigDecimal ret1 = nav.multiply(weight);
		BigDecimal ret2 = ret1.divide(price, 12, BigDecimal.ROUND_HALF_UP);
		int ret3 = ret2.compareTo(unit);
		if (ret3 == 0) {
			return unit;
		}
		logger.error("unit有誤");
		return ret2;
	}

	/**
	 * 表單自動化測試 -- 全品項預測
	 * 
	 * TEST2.xlsx 屬於 Type1型的預測資料檔
	 */
	public void paceE0T(String username, String password, String from, String to) {
		// PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
		// username = "jacky.lee@ebizprise.com";
		// username = "pkpkpk";
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
		
		schemaList.add("IE00BYXYYJ35");
		schemaList.add("IE00B7N3YW49");

		pace9_1(username, password, from, to, schemaList, path);
		// pace0_3(paceStyle);
	}

	public void paceE1T(String username, String password, String from, String to) {
		// PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
		// username = "jacky.lee@ebizprise.com";
		// username = "pkpkpk";
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
		
		schemaList.add("IE00B7N3YW49");
		
		pace9_1(username, password, from, to, schemaList, path);
		// pace0_3(paceStyle);
	}

	public void paceE2T(String username, String password, String from, String to) {
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
		schemaList.add("IE00B7N3YW49");
		
		pace9_1(username, password, from, to, schemaList, path);
	}

	public void paceE3T(String username, String password, String from, String to) {
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
		schemaList.add("IE00BYXYYJ35");
		schemaList.add("IE00B7N3YW49");

		pace9_1(username, password, from, to, schemaList, path);
	}

	public void paceE4T(String username, String password, String from, String to) {
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
		schemaList.add("IE00BYXYYJ35");
		schemaList.add("IE00B7N3YW49");
		pace9_1(username, password, from, to, schemaList, path);
	}

	public void paceE11T(String username, String password, String from, String to) {
		// PaceStyle paceStyle = paceStyleFactory.getStyle("TEST1");
		// username = "jacky.lee@ebizprise.com";
		// username = "pkpkpk";
		String path = "/tmp/E11T.xlsx";

		List<String> schemaList = new ArrayList();
		schemaList.add("IE0032077012");

		pace9_1(username, password, from, to, schemaList, path);
		// pace0_3(paceStyle);
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
	private void pace9_1(String username, String password, String from, String to, List<String> schemaList,
			String path) {
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
			mp1.put("data-source", "bloomberg");
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
		String sheetName4 = "Sheet 4";
		ep.outExcelposition("Date", sheetName1, 0, 0, path);
		ep.outExcelposition("Date", sheetName2, 0, 0, path);
		for (int i = 0; i < productFormList.size(); i++) {
			ProductForm productForm = productFormList.get(i);
			String assetId = productForm.getAssetId();

			List priceList = paceDefault.pace13(token, assetId, from, to);
			String schema = schemaList.get(i);
			ep.outExcelposition(schema, sheetName1, 0, i + 1, path);
			ep.outExcelposition(schema, sheetName2, 0, i * 2 + 1, path);
			logger.info(schema);
			showPrice(priceList, ep, sheetName1, path, i + 1, sheetName2);

			List listDividends = new ArrayList();
			List dvdList = paceDefault.pace14(token, assetId, from, to, listDividends);
			ep.outExcelposition(schema, sheetName3, 0, i + 1, path);
			ep.outExcelposition(schema, sheetName4, 0, i + 1, path);
			showDvd(dvdList, ep, sheetName3, path, i + 1);
			showDvds(listDividends, ep, sheetName4, path, i + 1);
		}

	}

	private void showDvds(List listDividends, ExcelPOIXml ep, String sheetName4, String path, int field) {
		for (int i = 0; i < listDividends.size(); i++) {
			JSONObject jSONObject = (JSONObject) listDividends.get(i);
			DvdForm2 dvdForm2 = JSON.parseObject(jSONObject.toJSONString(), DvdForm2.class);
			logger.info(dvdForm2.getExDate() + " : " + dvdForm2.getValue());
			ep.outExcelposition(dvdForm2.getValue(), sheetName4, i + 1, field, path);
			ep.outExcelposition(dvdForm2.getExDate(), sheetName4, i + 1, 0, path);
		}
	}

	private void pace9_2(String username, String password, String targetDate, ModelPortfolio E9T, String path) {
		PriveAuthForm priveAuthForm = paceDefault.pace11(username, password);
		String access_token = priveAuthForm.getAccess_token();
		String token_type = priveAuthForm.getToken_type();
		Assert.assertNotNull(token_type);
		Assert.assertNotNull(access_token);

		List<Portfolio> portfolioList = E9T.getPortfolioList();

		String token = token_type + " " + access_token;

		Date tDate = DateUtil.str2Date(targetDate, "yyyy-MM-dd");
		Date eDate = DateUtil.add(tDate, 1);
		String to = DateUtil.date2str(eDate, "yyyy-MM-dd");

		for (int i = 0; i < portfolioList.size(); i++) {
			Portfolio portfolio = portfolioList.get(i);
			Map mp1 = new HashMap();
			mp1.put("scheme", "ISIN");
			mp1.put("value", portfolio.getIsin());
			mp1.put("currency", "USD");
			mp1.put("data-source", "bloomberg");
			List queryList = new ArrayList();
			queryList.add(mp1);
			List<ProductForm> productFormList = paceDefault.pace12(token, queryList);

			if (productFormList.size() != 1) {
				logger.error("沒asset ID");
			} else {
				ProductForm productForm = productFormList.get(0);
				String assetId = productForm.getAssetId();
				portfolio.setAssetId(assetId);

				List priceList = paceDefault.pace13(token, assetId, "2019-05-26", to);
				PriceForm2 priceForm = (PriceForm2) priceList.get(0);
				BigDecimal closePx = takePrice(priceList, targetDate);
				// logger.info(priceForm.getLocalDate() + " : " +
				// priceForm.getClosePx());
				// BigDecimal closePx = new BigDecimal(priceForm.getClosePx());
				portfolio.setClosePx(closePx);
				portfolio.setLocalDate(priceForm.getLocalDate());

				List listDividends = new ArrayList();
				List dvdList = paceDefault.pace14(token, assetId, "2019-05-26", to, listDividends);

				BigDecimal dvd = takeDvd(dvdList, targetDate);
				portfolio.setDvd(dvd);
			}
		}

	}

	private BigDecimal takePrice(List priceList, String from) {
		String targetDate = StringUtils.replace(from, "-", "");
		for (int i = 0; i < priceList.size(); i++) {
			PriceForm2 priceForm2 = (PriceForm2) priceList.get(i);
			if (targetDate.equals(priceForm2.getLocalDate())) {
				if (priceForm2.getClosePx() == null)
					return new BigDecimal(0);
				BigDecimal price = new BigDecimal(priceForm2.getClosePx());
				return price;
			}
		}
		logger.error("price沒有涵蓋到");
		return new BigDecimal(0);
	}

	private void showCurrencyNav(ModelPortfolio E9T) {
		BigDecimal nav = E9T.calcNav();
		BigDecimal b = nav.setScale(6, BigDecimal.ROUND_HALF_UP);
		logger.warn(E9T.getModelName() + "  " + E9T.getTargetDate() + "  " + b);

	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step0_1: 共同部份,每個交易前5步驟都會一樣
	 */
	private void pace0_1(String username, String password, String path) {
		// PriveAuthForm priveAuthForm = paceDefault.pace11(username, password);
		// String access_token = priveAuthForm.getAccess_token();
		// String token_type = priveAuthForm.getToken_type();
		// Assert.assertNotNull(token_type);
		// Assert.assertNotNull(access_token);
		//
		// List<String> schemaList = new ArrayList();
		// schemaList.add("IE0005042456");
		// schemaList.add("IE00B53QG562");
		// schemaList.add("IE00B2QWCY14");
		// schemaList.add("IE00B5BMR087");
		// schemaList.add("LU1781541252");
		// schemaList.add("IE00B52MJY50");
		// schemaList.add("IE00BKM4GZ66");
		// schemaList.add("IE00B52SF786");
		// schemaList.add("IE00BSKRJZ44");
		// schemaList.add("IE00BZ163M45");
		// schemaList.add("IE00B2NPKV68");
		// schemaList.add("LU1452600270");
		// schemaList.add("IE00BCRY6227");
		// schemaList.add("IE00BCRY5Y77");
		// schemaList.add("IE00B2QWDY88");
		// schemaList.add("IE0032077012");
		// schemaList.add("LU1407889887");
		//
		// List queryList = new ArrayList();
		// for (int i = 0; i < schemaList.size(); i++) {
		// String schema = schemaList.get(i);
		// Map mp1 = new HashMap();
		// mp1.put("scheme", "ISIN");
		// mp1.put("value", schema);
		// mp1.put("currency", "USD");
		// queryList.add(mp1);
		// }
		// String token = token_type + " " + access_token;
		//
		// List<ProductForm> productFormList = paceDefault.pace12(token,
		// queryList);
		// List lines = new ArrayList();
		//
		// ExcelPOIXml ep = new ExcelPOIXml();
		// ep.outExcel(lines, path);
		// String sheetName = "Sheet 1";
		// String sheetName2 = "Sheet 2";
		// ep.outExcelposition("Date", sheetName, 0, 0, path);
		// ep.outExcelposition("Date", sheetName2, 0, 0, path);
		// for (int i = 0; i < productFormList.size(); i++) {
		// ProductForm productForm = productFormList.get(i);
		// String assetId = productForm.getAssetId();
		// String from = "2019-05-01";
		// String to = "2019-05-13";
		// List priceList = paceDefault.pace13(token, assetId, from, to);
		// String schema = schemaList.get(i);
		// ep.outExcelposition(schema, sheetName, 0, i + 1, path);
		// ep.outExcelposition(schema, sheetName2, 0, i * 2 + 1, path);
		// logger.info(schema);
		// showPrice(priceList, ep, sheetName, path, i + 1, sheetName2);
		// }

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

	private BigDecimal takeDvd(List dvdList, String from) {
		String targetDate = StringUtils.replace(from, "-", "");
		for (int i = 0; i < dvdList.size(); i++) {
			DvdForm2 dvdForm2 = (DvdForm2) dvdList.get(i);
			if (targetDate.equals(dvdForm2.getExDate())) {
				if (dvdForm2.getValue() == null)
					return new BigDecimal(0);
				BigDecimal dvd = new BigDecimal(dvdForm2.getValue());
				return dvd;
			}
		}
		logger.error("dvd沒有涵蓋到");
		return new BigDecimal(0);
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
