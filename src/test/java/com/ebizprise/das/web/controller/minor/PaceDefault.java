package com.ebizprise.das.web.controller.minor;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebizprise.das.form.base.DvdForm;
import com.ebizprise.das.form.base.DvdForm2;
import com.ebizprise.das.form.base.PriceForm;
import com.ebizprise.das.form.base.PriceForm2;
import com.ebizprise.das.form.base.PriveAuthForm;
import com.ebizprise.das.form.base.ProductForm;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.pojo.Portfolio;
import com.ebizprise.das.utils.DateUtil;

//import com.google.common.collect.Maps;

/**
 * 功能元件 提供基本公用功能函數
 * 
 * @author jacky.lee
 *
 */
@Component
public class PaceDefault extends PaceBase {

	private static final Log logger = LogFactory.getLog(PaceDefault.class);

	// @Autowired
	// protected ForecastParameter forecastParameter;

	public PaceDefault() {
		super();
	}

	/**
	 * 反射函數 -- 統計步驟時間用
	 */
	public Object pace(String methodName, Class[] argType, Object[] args) {
		Object value = null;
		Method method;
		Class<?> enclosingClass = getClass();
		if (enclosingClass != null) {
			try {
				logger.warn(methodName + " 開始 ");
				long begintime = System.currentTimeMillis();

				method = enclosingClass.getDeclaredMethod(methodName, argType);
				value = method.invoke(this, args);

				long endtime = System.currentTimeMillis();
				long costTime = (endtime - begintime);
				logger.warn(methodName + " 耗時 " + costTime / 1000 + " 秒");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_1: 先產生驗證碼
	 */
	public PriveAuthForm pace11(String username, String password) {
		String url = FPUtil.makeUrl("/auth/1/login");

		Map<String, String> params = new HashMap();
		params.put("username", username);
		params.put("password", password);

		ResponseEntity<String> response = paceType(url, HttpMethod.POST, params);
		// 輸出結果
		logger.info(response.getBody());
		PriveAuthForm priveAuthForm = JSON.parseObject(response.getBody(), PriveAuthForm.class);

		return priveAuthForm;
	}

	/**
	 * 取得全部基金的對應code
	 */
	public List<ProductForm> pace12(String token, List<Map> queryList) {
		String url = FPUtil.makeUrl("/assets/1/fubon-tw/codes");
		ResponseEntity<String> response = paceType(token, url, HttpMethod.POST, queryList);
		// 輸出結果
		logger.info(response.getBody());
		List productList = JSON.parseObject(response.getBody(), List.class);
		List productFormList = new ArrayList();

		for (int i = 0; i < productList.size(); i++) {
			JSONObject product = (JSONObject) productList.get(i);
			ProductForm productForm = JSON.parseObject(product.toJSONString(), ProductForm.class);
			productFormList.add(productForm);
		}
		return productFormList;
	}

	/**
	 * 取得特定基金的時段收盤價
	 */
	public List pace13(String token, String assetId, String from, String to) {
		String url = FPUtil.makeUrl("/assets/1/fubon-tw/assets/" + assetId + "/series?from=" + from + "&until=" + to
				+ "&type=daily&priceDataType=RAW");
		Map<String, ?> params = new HashMap();
		ResponseEntity<String> response = paceType(token, url, HttpMethod.GET, params);
		// 輸出結果
		logger.info(response.getBody());
		PriceForm priceForm = JSON.parseObject(response.getBody(), PriceForm.class);
		List list = priceForm.getPrices();
		List priceList = new ArrayList();
		Date dFrom = DateUtil.str2Date(from, "yyyy-MM-dd");
		Date dTo = DateUtil.str2Date(to, "yyyy-MM-dd");
		for (Date dIdx = dFrom; DateUtil.LessEqual(dIdx, dTo); dIdx = DateUtil.add(dIdx, 1)) {
			PriceForm2 priceForm2 = queryPrice(list, dIdx);
			if (priceForm2 != null)
				priceList.add(priceForm2);
		}

		return priceList;
	}

	/**
	 * 取得特定基金的時段配股
	 */
	public List pace14(String token, String assetId, String from, String to, List listDividends) {
		String url = FPUtil.makeUrl(
				"/assets/1/fubon-tw/assets/" + assetId + "/dividends?from=" + from + "&until=" + to + "&type=daily");
		Map<String, ?> params = new HashMap();
		ResponseEntity<String> response = paceType(token, url, HttpMethod.GET, params);
		// 輸出結果
		logger.info(response.getBody());
		DvdForm dvdForm = JSON.parseObject(response.getBody(), DvdForm.class);
		List list = dvdForm.getDividends();
		List dvdList = new ArrayList();
		Date dFrom = DateUtil.str2Date(from, "yyyy-MM-dd");
		Date dTo = DateUtil.str2Date(to, "yyyy-MM-dd");
		for (Date dIdx = dFrom; DateUtil.LessEqual(dIdx, dTo); dIdx = DateUtil.add(dIdx, 1)) {
			DvdForm2 dvdForm2 = queryDvd(list, dIdx);
			dvdList.add(dvdForm2);
		}
		listDividends.addAll(list);
		return dvdList;
	}

	private DvdForm2 queryDvd(List list, Date dIdx) {
		DvdForm2 keep = new DvdForm2();
		for (int i = 0; i < list.size(); i++) {
			JSONObject dvd = (JSONObject) list.get(i);
			DvdForm2 dvdForm2 = JSON.parseObject(dvd.toJSONString(), DvdForm2.class);
			String localDate = dvdForm2.getExDate();
			Date dlocalDate = DateUtil.str2Date(localDate, "yyyyMMdd");
			if (DateUtil.LessEqual(dlocalDate, dIdx))
				keep = new DvdForm2(dvdForm2);
			if (DateUtil.Equ(dlocalDate, dIdx))
				return dvdForm2;
		}
		if (keep != null)
			keep.setExDate(DateUtil.date2str(dIdx, "yyyyMMdd"));
		return keep;
	}

	private PriceForm2 queryPrice(List list, Date dIdx) {
		PriceForm2 keep = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject price = (JSONObject) list.get(i);
			PriceForm2 priceForm2 = JSON.parseObject(price.toJSONString(), PriceForm2.class);
			String localDate = priceForm2.getLocalDate();
			Date dlocalDate = DateUtil.str2Date(localDate, "yyyyMMdd");
			if (DateUtil.LessEqual(dlocalDate, dIdx))
				keep = new PriceForm2(priceForm2);
			if (DateUtil.Equ(dlocalDate, dIdx))
				return priceForm2;
		}
		String targetDate = DateUtil.date2str(dIdx, "yyyyMMdd");
		if (keep == null) {
			logger.warn(targetDate + " 沒資料 還沒開始有資料");
			return keep;
		}
		logger.warn(targetDate + " 沒資料,用 " + keep.getLocalDate() + " 代替");
		keep.setLocalDate(targetDate);
		return keep;
	}

	public Portfolio queryPortfolio(List<Portfolio> listPortfolio, Date dIdx) {
		Portfolio keep = null;
		for (int i = 0; i < listPortfolio.size(); i++) {
			Portfolio portfolio = listPortfolio.get(i);
			String localDate = portfolio.getLocalDate();
//			if ("20160101".equals(localDate)) {
//				String tDate = DateUtil.date2str(dIdx, "yyyyMMdd");
//				if ("20160101".equals(tDate))
//					logger.error("why");
//			}
			Date dlocalDate = DateUtil.str2Date(localDate, "yyyyMMdd");
			if (DateUtil.LessEqual(dlocalDate, dIdx))
				keep = new Portfolio(portfolio);
			if (DateUtil.Equ(dlocalDate, dIdx))
				return portfolio;
		}
		if (keep != null)
			keep.setLocalDate(DateUtil.date2str(dIdx, "yyyyMMdd"));
		return keep;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_5: 上傳預測資料檔案
	 */
	public QueryForm pace15(String filePath, String custId, String prjItmId) {
		String url = FPUtil.makeUrl("/upload/");

		logger.warn("/upload/ 開始 ");
		long begintime = System.currentTimeMillis();

		ResponseEntity<String> response = fileUpload(filePath, url, custId, prjItmId);

		long endtime = System.currentTimeMillis();
		long costTime = (endtime - begintime);
		logger.warn("/upload/ 耗時 " + costTime / 1000 + " 秒");

		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(), QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step3:
	 */
	// private QueryForm pace3(String custId, String prjItmId) {
	// String url = CommonUtils.PRIFIX + "/prjImportStep3/";
	//
	// List obja = new ArrayList();
	// obja.add("col001");
	// obja.add("col002");
	// obja.add("col003");
	//
	// Map<String, Object> params = Maps.newHashMap();
	// params.put("custId", custId);
	// params.put("prjItmId", prjItmId);
	// params.put("prjDtlId", prjItmId + "-01");
	// params.put("sltCol", obja);
	//
	// ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
	// // 輸出結果
	// logger.info(response.getBody());
	// QueryForm queryForm = JSON.parseObject(response.getBody(),
	// QueryForm.class);
	//
	// return queryForm;
	// }

	private ResponseEntity<String> fileUpload(String filePath, String url, String custId, String prjItmId) {

		URL urlfile = this.getClass().getResource(filePath);
		File file = new File(urlfile.getFile());
		FileSystemResource resource = new FileSystemResource(file);
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("file", resource);
		param.add("custId", custId);
		param.add("prjItmId", prjItmId);
		param.add("type", 1);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return response;
	}

	// private <T> Object transformJsonToBean(String json, Class<T> type) {
	// // 一个简单方便 的方法将Json文本信息转换为JsonObject对象的同时转换为JavaBean对象！
	// return JSON.parseObject(json, type);// Weibo类在下边定义
	// }

}
