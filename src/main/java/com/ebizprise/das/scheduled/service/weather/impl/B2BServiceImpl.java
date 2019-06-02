package com.ebizprise.das.scheduled.service.weather.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebizprise.das.form.base.DvdForm;
import com.ebizprise.das.form.base.DvdForm2;
import com.ebizprise.das.form.base.PortfolioForm;
import com.ebizprise.das.form.base.PortfolioSeriesForm;
import com.ebizprise.das.form.base.PriceForm;
import com.ebizprise.das.form.base.PriceForm2;
import com.ebizprise.das.form.base.PriveAuthForm;
import com.ebizprise.das.form.base.ProductForm;
import com.ebizprise.das.scheduled.service.weather.B2BService;
import com.ebizprise.das.utils.DateUtil;
import com.ebizprise.das.web.controller.minor.FPUtil;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
@Service
public class B2BServiceImpl extends RestServiceImpl implements B2BService {
	private static final Log logger = LogFactory.getLog(B2BServiceImpl.class);

	private static String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJwcml2ZSJ9.bN-CANKXKbjq_-0AOsK7goL4usRLrK13Qbonx00WBM5ymIOZPGncYvC3jA5YuxoJNl_usX_pDMSWES7I-P94ZvC-ir67rsaI6Seb4pLWSrNqn4vjjjQc9gMXwHiJgNUFpqlyyIbONknLYtrDPMd0Y4K6mibgyUlukekal6etw0uU8RhgLp1kZq7YDbgpOmIuk80R1ZRiOcYYLrzlnzPiW-REXSuqlQcs93Ppd8q971ToI0z6OJcfUn0f4NYJUEaDD30ILpwkAvkOgrMkbRePHU2Ai3E3Qkz2nYUKW2NlEql5XATpU59lWGaShuXeP__4Wj344SetwToeeHfty2yjrA";
	private static String microServiceHost = "http://api-micro-uat.privemanagers.com";// "https://tw-api-micro-uat.privemanagers.com";
																						// //
																						// "https://tw-api-micro.privemanagers.com";

	private String takeToken() {
		if (!"".equals(token))
			return token;

		logger.info("尚未登入,進行取得token做業");

		String url = microServiceHost + "/auth/1/login";

		Map<String, String> params = new HashMap();
		params.put("username", "prive_admin");
		params.put("password", "j@8u&SQw1!");

		ResponseEntity<String> response = paceType(url, HttpMethod.POST, params);
		// 輸出結果
		logger.info(response.getBody());
		PriveAuthForm priveAuthForm = JSON.parseObject(response.getBody(),
				PriveAuthForm.class);

		String access_token = priveAuthForm.getAccess_token();
		String token_type = priveAuthForm.getToken_type();

		token = token_type + " " + access_token;
		logger.info("token:" + token);
		return token;
	}

	private PortfolioForm readPortfolioNav(String portfolio) {
		String url = microServiceHost + "/b2b/1/fubon-tw/models/" + portfolio
				+ "_USD?series=true";

		Map<String, ?> params = new HashMap();
		ResponseEntity<String> response = paceType(takeToken(), url,
				HttpMethod.GET, params);

		// 輸出結果
		logger.debug(response.getBody());
		PortfolioForm portfolioForm = JSON.parseObject(response.getBody(),
				PortfolioForm.class);

		return portfolioForm;
	}

	private PortfolioForm writePortfolioNav(String portfolio, String date,
			String nav) {
		String url = microServiceHost + "/assets/1/fubon-tw/raw/price";

		Map<String, Object> primaryCode = new HashMap();
		primaryCode.put("value", portfolio + "_USD");
		primaryCode.put("scheme", "fubon");

		Map<String, Object> map1 = new HashMap();
		map1.put("date", date);
		map1.put("value", nav);

		List<Map<String, Object>> timeSeries = new ArrayList();
		timeSeries.add(map1);

		Map<String, Object> map2 = new HashMap();
		map2.put("primaryCode", primaryCode);
		map2.put("timeSeries", timeSeries);

		List<Map<String, Object>> assetPriceSeriesList = new ArrayList();
		assetPriceSeriesList.add(map2);

		Map<String, Object> params = new HashMap();
		params.put("assetPriceSeriesList", assetPriceSeriesList);
		params.put("priceDataType", "ADJUSTED");
		params.put("dataSource", "fubon-tw");
		params.put("source", "manual");

		ResponseEntity<String> response = paceType(takeToken(), url,
				HttpMethod.POST, params);

		// 輸出結果
		logger.debug(response.getBody());
		PortfolioForm portfolioForm = JSON.parseObject(response.getBody(),
				PortfolioForm.class);

		return portfolioForm;
	}

	public PriveAuthForm writePortfolioNavToday() {
		String date = "20190530";
		String E1_nav = "1000000.0";

		PortfolioForm portfolioForm_E1 = writePortfolioNav("E1", date, E1_nav);

		return null;
	}

	public PriveAuthForm readPortfolioNavLatest(int num) {
		PortfolioForm portfolioForm_E1T = readPortfolioNav("E1T");
		showNav(portfolioForm_E1T, num);

		PortfolioForm portfolioForm_E2T = readPortfolioNav("E2T");
		showNav(portfolioForm_E2T, num);

		PortfolioForm portfolioForm_E3T = readPortfolioNav("E3T");
		showNav(portfolioForm_E3T, num);

		PortfolioForm portfolioForm_E4T = readPortfolioNav("E4T");
		showNav(portfolioForm_E4T, num);

		return null;
	}

	public PriveAuthForm readPortfolioBackTestingNavLatest(int num) {
		PortfolioForm portfolioForm_E1T = readPortfolioNav("E1");
		showNav(portfolioForm_E1T, num);

		PortfolioForm portfolioForm_E2T = readPortfolioNav("E2");
		showNav(portfolioForm_E2T, num);

		PortfolioForm portfolioForm_E3T = readPortfolioNav("E3");
		showNav(portfolioForm_E3T, num);

		PortfolioForm portfolioForm_E4T = readPortfolioNav("E4");
		showNav(portfolioForm_E4T, num);

		return null;
	}

	private void showNav(PortfolioForm pf, int num) {
		pf.showDetail();
		List<PortfolioSeriesForm> list = pf.getSeries();
		for (int i = list.size() - num; i < list.size(); i++) {
			PortfolioSeriesForm psf = list.get(i);
			logger.info("date:" + psf.getMonth() + " nav:" + psf.getValue());
		}
	}

	/**
	 * 取得全部基金的對應code
	 */
	public List<ProductForm> pace12(List<Map> queryList) {
		String url = FPUtil.makeUrl("/assets/1/fubon-tw/codes");
		ResponseEntity<String> response = paceType(takeToken(), url,
				HttpMethod.POST, queryList);
		// 輸出結果
		logger.info(response.getBody());
		List productList = JSON.parseObject(response.getBody(), List.class);
		List productFormList = new ArrayList();

		for (int i = 0; i < productList.size(); i++) {
			JSONObject product = (JSONObject) productList.get(i);
			ProductForm productForm = JSON.parseObject(product.toJSONString(),
					ProductForm.class);
			productFormList.add(productForm);
		}
		return productFormList;
	}

	/**
	 * 取得特定基金的時段收盤價
	 */
	public List pace13(String assetId, String from, String to) {
		String url = FPUtil.makeUrl("/assets/1/fubon-tw/assets/" + assetId
				+ "/series?from=" + from + "&until=" + to
				+ "&type=daily&priceDataType=RAW");
		Map<String, ?> params = new HashMap();
		ResponseEntity<String> response = paceType(takeToken(), url,
				HttpMethod.GET, params);
		// 輸出結果
		logger.info(response.getBody());
		PriceForm priceForm = JSON.parseObject(response.getBody(),
				PriceForm.class);
		List list = priceForm.getPrices();
		List priceList = new ArrayList();
		Date dFrom = DateUtil.str2Date(from, "yyyy-MM-dd");
		Date dTo = DateUtil.str2Date(to, "yyyy-MM-dd");
		for (Date dIdx = dFrom; DateUtil.Less(dIdx, dTo); dIdx = DateUtil.add(
				dIdx, 1)) {
			PriceForm2 priceForm2 = queryPrice(list, dIdx);
			if (priceForm2 != null)
				priceList.add(priceForm2);
		}

		return priceList;
	}

	/**
	 * 取得特定基金的時段配股
	 */
	public List pace14(String assetId, String from, String to,
			List listDividends) {
		String url = FPUtil.makeUrl("/assets/1/fubon-tw/assets/" + assetId
				+ "/dividends?from=" + from + "&until=" + to + "&type=daily");
		Map<String, ?> params = new HashMap();
		ResponseEntity<String> response = paceType(takeToken(), url,
				HttpMethod.GET, params);
		// 輸出結果
		logger.info(response.getBody());
		DvdForm dvdForm = JSON.parseObject(response.getBody(), DvdForm.class);
		List list = dvdForm.getDividends();
		List dvdList = new ArrayList();
		Date dFrom = DateUtil.str2Date(from, "yyyy-MM-dd");
		Date dTo = DateUtil.str2Date(to, "yyyy-MM-dd");
		for (Date dIdx = dFrom; DateUtil.Less(dIdx, dTo); dIdx = DateUtil.add(
				dIdx, 1)) {
			DvdForm2 dvdForm2 = queryDvd(list, dIdx);
			dvdList.add(dvdForm2);
		}
		listDividends.addAll(list);
		return dvdList;
	}

}
