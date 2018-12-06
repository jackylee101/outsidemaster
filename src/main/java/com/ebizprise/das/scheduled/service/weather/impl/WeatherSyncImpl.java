package com.ebizprise.das.scheduled.service.weather.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import com.ebizprise.das.WebApplication;
import com.ebizprise.das.db.entity.DailyWeather;
import com.ebizprise.das.db.model.MyMap;
import com.ebizprise.das.db.repository.DailyWeatherRepository;
import com.ebizprise.das.scheduled.service.weather.WeatherSync;
import com.ebizprise.das.utils.CommonUtils;
import com.ebizprise.das.utils.DateUtil;
import com.ebizprise.das.utils.HttpUtils;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
@Service
public class WeatherSyncImpl implements WeatherSync {
	private static final Logger logger = LoggerFactory
			.getLogger(WebApplication.class);
	private String etl_date;
	private JSONArray codeList;

	@Autowired
	ConfigurableEnvironment env;

	// @Autowired
	// private OutsideConfig outsideConfig;
	//
	// @Autowired
	// private CodeUtilsCommon codeUtilsCommon;

	// @Autowired
	// private RedisRepository redisRepository;

	@Autowired
	private DailyWeatherRepository dailyWeatherRepository;

	// ========================================================================
	// 同步都市列表的來源
	public String getSyncCityList() {
		String syncCityList = env.getProperty("baseUrl.syncCityList");
		return syncCityList;
	}

	// 同步都市資料的上限,用於測試模式限制筆數用,正式環境加大到超過3xx即可(prod:65534)
	public int getSyncCities() {
		int syncCities = Integer
				.parseInt(env.getProperty("baseUrl.syncCities"));
		return syncCities;
	}

	// 同步功能啟動,若為false,則僅執行捕捉showapi資料而已
	public boolean isSyncEnable() {
		boolean syncEnable = Boolean.parseBoolean(env
				.getProperty("baseUrl.syncEnable"));
		return syncEnable;
	}

	// 同步哪些環境的資料,(prod,staging,dev)為自動模式預設值
	public String getSyncServer() {
		String syncServer = env.getProperty("baseUrl.syncServer");
		return syncServer;
	}

	// 環境prod路徑
	public String getSyncUrl1() {
		String syncUrl1 = env.getProperty("baseUrl.syncUrl1");
		return syncUrl1;
	}

	// 環境staging路徑
	public String getSyncUrl2() {
		String syncUrl2 = env.getProperty("baseUrl.syncUrl2");
		return syncUrl2;
	}

	// 環境dev路徑
	public String getSyncUrl3() {
		String syncUrl3 = env.getProperty("baseUrl.syncUrl3");
		return syncUrl3;
	}

	// ========================================================================

	// @Override
	/**
	 * 天氣資料同步作業--自動化排程驅動的入口點
	 */
	public void takeWeatherDate2DB() {
		// 決定ETL_DATE 每天初次執行可以做一次,接著手動都不能再觸發,以避免跨日意外
		String etlDate = DateUtil.acquireEtlDate(0);
		// String syncServer = "prod,staging,dev";
		takeWeatherDate2DB(etlDate, getSyncServer());
	}

	/**
	 * 天氣資料同步作業--主入口兼手動驅動入口
	 */
	public void takeWeatherDate2DB(String etlDate, String syncServer) {
		this.etl_date = etlDate;
		if (syncServer == null)// 手動如果沒有指定環境
			syncServer = "dev";// 就只會讓dev環境執行同步更新

		if (isSyncEnable()) {
			if (StringUtils.indexOf(syncServer, "prod") >= 0) {
				updateEtlDatefor(getSyncUrl1());// 更新ETL基準日Prod
			}
			if (StringUtils.indexOf(syncServer, "staging") >= 0) {
				updateEtlDatefor(getSyncUrl2());// 更新ETL基準日dev
			}
			if (StringUtils.indexOf(syncServer, "dev") >= 0) {
				updateEtlDatefor(getSyncUrl3());// 更新ETL基準日dev
			}
		} else {
			logger.warn("同步不執行,syncEnable: " + isSyncEnable());
		}
		String cityListSrc = takeSrc(getSyncCityList());

		if (!obtainCityIds(cityListSrc))
			return;
		if (!mession6())
			return;
		if (!mession7())
			return;

		if (isSyncEnable()) {
			if (StringUtils.indexOf(syncServer, "prod") >= 0) {
				syncServer(getSyncUrl1());// 開始同步Prod資料
			}
			if (StringUtils.indexOf(syncServer, "staging") >= 0) {
				syncServer(getSyncUrl2());// 開始同步Staging資料
			}
			if (StringUtils.indexOf(syncServer, "dev") >= 0) {
				syncServer(getSyncUrl3());// 開始同步Dev資料
			}
		}
	}

	/**
	 * 根據指定字串回傳該環境的URL
	 * 
	 * @param env
	 * @return
	 */

	private String takeSrc(String env) {
		if (StringUtils.indexOf(env, "prod") >= 0)
			return getSyncUrl1();
		if (StringUtils.indexOf(env, "staging") >= 0)
			return getSyncUrl2();
		if (StringUtils.indexOf(env, "dev") >= 0)
			return getSyncUrl3();

		logger.error("錯誤!沒有指定來源: " + env);
		return "";
	}

	/*
	 * 
	 * 更新ETL基準日Prod
	 */
	private boolean updateEtlDatefor(String host) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiUpdateETL_date/"
				+ this.etl_date;
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("403");
				b = false;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			logger.info(objectStr);
			JSONObject jsonObject = new JSONObject(objectStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("Update ETL date for " + host + " result:" + b);
			return b;
		}

	}

	/*
	 * 
	 * 由ShowAPI取得指定城市
	 */
	protected boolean obtainCityIds(String host) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiSelectAPI_City_ID_Mapping";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("403");
				b = false;
			} else if (statusLine.getStatusCode() == 404) {
				logger.error("404");
				b = false;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			JSONObject jsonObject = new JSONObject(objectStr);
			codeList = (JSONArray) jsonObject.get("codeList");
			// JSONObject hsin = codeList.getJSONObject(307);
			// codeList = new JSONArray();
			// codeList.put(0, hsin);

			String msgCode = (String) jsonObject.get("msgCode");
			if ("error".equals(msgCode)) {
				String msgDesc = (String) jsonObject.get("msgDesc");
				logger.error(msgDesc);
				b = false;
			} else {
				logger.debug(objectStr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("api SelectAPI City_ID_Mapping from " + host
					+ " result:" + b);
			return b;
		}
	}

	/*
	 * 
	 * 指定城市321之一
	 */
	private boolean mession6() {
		int sum = api3rdWData(this.etl_date, new StringBuffer());
		// if (sum == 0)
		// return false;
		return true;
	}

	/*
	 * 
	 * 指定城市320之一 如果出現exception(最常見是redis忘了開) 會把訊息印出
	 */
	private String mession6test() {
		StringBuffer sb = new StringBuffer();
		int sum = api3rdWData_test(this.etl_date, sb);
		if (sum == -1)
			return sb.toString();
		return "";
	}

	/*
	 * 
	 * 指定城市321之2
	 */
	private boolean mession7() {
		int sum = api3rdWD15(this.etl_date, new StringBuffer());
		return true;
	}

	/*
	 * 
	 * 開始同步Prod資料
	 */
	private boolean syncServer(String syncUrl) {
		boolean b1 = mession9(syncUrl);
		if (!b1)
			logger.error("七日預測數據同步有誤: " + syncUrl);

		boolean b2 = mession10(syncUrl);
		if (!b2)
			logger.error("15日預測數據同步有誤: " + syncUrl);

		boolean b3 = mession11(syncUrl);
		if (!b3)
			logger.error("呼叫sp有誤: " + syncUrl);
		return b3;
	}

	/*
	 * 
	 * 觸發321城市資料填充之1
	 */
	private boolean mession9(String host) {
		return apiInsertWEATHER_DATA(host, this.etl_date, new StringBuffer());
	}

	/*
	 * 
	 * 觸發321城市資料填充之2
	 */
	private boolean mession10(String host) {
		return apiInsertWEATHER_DATA_PRE15(host, this.etl_date,
				new StringBuffer());
	}

	/*
	 * 
	 * 觸發SP
	 */
	private boolean mession11(String host) {
		return apiEXEC_SP(host, this.etl_date, new StringBuffer());
	}

	/**
	 * 對320個城市個別去查七日天氣預測
	 * 
	 * @param etlDate
	 * @param sb
	 * @return
	 */
	private Integer api3rdWData(String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + codeList.length() + " 個城市");
		for (int i = 0; i < codeList.length() && i < getSyncCities(); i++) {
			JSONObject jSONObject;
			try {
				jSONObject = codeList.getJSONObject(i);
				String c11 = jSONObject.getString("c11");
				String c12 = jSONObject.getString("c12");
				String areaid = jSONObject.getString("areaid");
				String area = jSONObject.getString("area");
				if ("".equals(c11)) {
					String ss = areaid + "(" + area + ")" + " 沒有c11";
					logger.error(ss);
					continue;
				}

				if ("".equals(c12)) {
					String ss = areaid + "(" + area + ")" + " 沒有c12";
					logger.warn(ss);
					// continue;
				}
				String key = "apiWData" + "_" + etlDate + "_" + areaid;
				if (!checkNowExist(key)) {
					logger.debug(i + ": " + areaid + " " + sum);
					String objectStr = api3rdWData(etlDate, areaid, c11, c12,
							sb);
					if (objectStr == null)
						continue;

					String message = area + "(" + areaid + "): " + objectStr;
					logger.info(message);

					objectStr = changeCityInfo(objectStr, jSONObject);

					// MyMap myMap = new MyMap("apiWData", key, objectStr);
					DailyWeather dailyWeather = new DailyWeather(key, objectStr);
					// redisRepository.add(myMap);
					dailyWeatherRepository.save(dailyWeather);
					sum += 1;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (RedisConnectionFailureException ex) {
				ex.printStackTrace();
				sb.append(ex.getMessage());
				return -1;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sum;
	}

	/**
	 * 對320個城市個別去查七日天氣預測 做測試
	 * 
	 * @param etlDate
	 * @param sb
	 * @return
	 */
	private Integer api3rdWData_test(String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + codeList.length() + " 個城市");
		for (int i = 0; i < codeList.length() && i < getSyncCities(); i++) {
			JSONObject jSONObject;
			try {
				jSONObject = codeList.getJSONObject(i);
				String c11 = jSONObject.getString("c11");
				String c12 = jSONObject.getString("c12");
				String areaid = jSONObject.getString("areaid");
				String area = jSONObject.getString("area");
				if ("".equals(c11)) {
					String ss = areaid + "(" + area + ")" + " 沒有c11";
					logger.error(ss);
					continue;
				}

				if ("".equals(c12)) {
					String ss = areaid + "(" + area + ")" + " 沒有c12";
					logger.warn(ss);
					// continue;
				}
				String key = "apiWData_" + etlDate + "_" + areaid;
				if (!checkNowExist(key)) {
					// logger.debug(i + ": " + areaid + " " + sum);
					// String objectStr = api3rdWData(etlDate, areaid, c11, c12,
					// sb);
					// if (objectStr == null)
					// continue;
					//
					// String message = area + "(" + areaid + "): " + objectStr;
					// logger.info(message);
					//
					// objectStr = changeCityInfo(objectStr, jSONObject);
					//
					// MyMap myMap = new MyMap("apiWData", id, objectStr);
					// redisRepository.add(myMap);
					//
					// sum += 1;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (RedisConnectionFailureException ex) {
				ex.printStackTrace();
				sb.append(ex.getMessage());
				return -1;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sum;
	}

	/**
	 * 有部分城市沒有自己的郵遞區號,必須對api回傳值加工替換掉
	 * 
	 * @param objectStr
	 * @param jSONObject
	 * @return
	 */
	private String changeCityInfo(String objectStr, JSONObject jSONObject) {
		JSONObject jsonObject2 = null;
		JSONObject showapi_res_body = null;
		JSONObject cityInfo = null;
		String robjectStr = objectStr;
		try {
			jsonObject2 = new JSONObject(objectStr);
			showapi_res_body = jsonObject2.getJSONObject("showapi_res_body");
			cityInfo = showapi_res_body.getJSONObject("cityInfo");

			String areaid2 = cityInfo.getString("c1");
			String areaid1 = jSONObject.getString("areaid");

			if (!areaid1.equals(areaid2)) {
				String c1 = jSONObject.getString("c1");
				String c2 = jSONObject.getString("c2");
				String c3 = jSONObject.getString("c3");
				cityInfo.put("c1", c1);
				cityInfo.put("c2", c2);
				cityInfo.put("c3", c3);
				robjectStr = jsonObject2.toString();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return robjectStr;
		}

	}

	/**
	 * 對320個城市個別去查15日天氣預測
	 * 
	 * @param etlDate
	 * @param sb
	 * @return
	 */
	private Integer api3rdWD15(String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + codeList.length() + " 個城市");
		for (int i = 0; i < codeList.length() && i < getSyncCities(); i++) {
			JSONObject jSONObject;
			try {
				jSONObject = codeList.getJSONObject(i);
				String c11 = jSONObject.getString("c11");
				String c12 = jSONObject.getString("c12");
				String areaid = jSONObject.getString("areaid");
				String area = jSONObject.getString("area");
				if ("".equals(c11)) {
					String ss = areaid + "(" + area + ")" + " 沒有c11";
					logger.error(ss);
					continue;
				}

				if ("".equals(c12)) {
					String ss = areaid + "(" + area + ")" + " 沒有c12";
					logger.warn(ss);
					// continue;
				}
				String key = "apiWD15_" + etlDate + "_" + areaid;
				if (!checkNowExist(key)) {
					logger.debug(i + ": " + areaid + " " + sum);
					String objectStr = api3rdWD15(etlDate, areaid, sb);
					if (objectStr == null)
						continue;

					DailyWeather dailyWeather = new DailyWeather(key, objectStr);
					dailyWeatherRepository.save(dailyWeather);
					sum += 1;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (RedisConnectionFailureException ex) {
				ex.printStackTrace();
				sb.append(ex.getMessage());
				return -1;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return sum;
	}

	/**
	 * 從redis檢查天氣資料是否已存在
	 * 
	 * @param key
	 * @param id
	 * @return
	 */
	// private boolean checkNowExist(String key, String id) throws Exception {
	// String myMap2 = redisRepository.findMyMap(key, id);
	// if (myMap2 == null)
	// return false;
	// else
	// return true;
	// }

	private boolean checkNowExist(String key) throws Exception {
		DailyWeather dailyWeather = dailyWeatherRepository.findOne(key);
		if (dailyWeather == null)
			return false;
		else
			return true;
	}

	/**
	 * 用郵遞區號查詢某城市七日天氣預報
	 * 
	 * @param etlDate
	 * @param areaid0
	 * @param phone_code
	 * @param post_code
	 * @param sb
	 * @return
	 */
	private String api3rdWData(String etlDate, String areaid0,
			String phone_code, String post_code, StringBuffer sb) {

		String host = "https://ali-weather.showapi.com";
		String path = "/phone-post-code-weeather";
		String method = "GET";
		String appcode = "5007796982e6452d9545063d90beda6d";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("need3HourForcast", "0");
		querys.put("needAlarm", "1");
		querys.put("needHourData", "0");
		querys.put("needIndex", "1");
		querys.put("needMoreDay", "1");
		querys.put("phone_code", phone_code);
		querys.put("post_code", post_code);
		HttpEntity httpEntity = null;

		// int sum = 0;
		// WeatherLocationMap weatherLocationMap = null;
		String objectStr = null;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("錢用光了" + response);
				return objectStr;
			} else if (statusLine.getStatusCode() == 503) {
				logger.error("網站掛掉了:" + response);
				return objectStr;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			logger.debug(objectStr);
			// JSONObject jsonObject = new JSONObject(objectStr);

		} catch (Exception e) {
			e.printStackTrace();
			String ss = "地區";
			ss += areaid0 + ":";
			ss += "發生錯誤1,";
			sb.append(ss);
			logger.error(ss, e);
		}

		return objectStr;
	}

	/**
	 * 用地區代號查詢某城市15日天氣預報
	 * 
	 * @param etlDate
	 * @param areaid
	 * @param sb
	 * @return
	 */
	private String api3rdWD15(String etlDate, String areaid, StringBuffer sb) {
		String host = "https://ali-weather.showapi.com";
		String path = "/day15";
		String method = "GET";
		String appcode = "5007796982e6452d9545063d90beda6d";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		// querys.put("area", area);
		querys.put("areaid", areaid);
		HttpEntity httpEntity = null;

		// int sum = 0;
		String objectStr = null;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("錢用光了" + response);
				return objectStr;
			} else if (statusLine.getStatusCode() == 503) {
				logger.error("網站掛掉了:" + response);
				return objectStr;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			logger.debug(objectStr);
			// JSONObject jsonObject = new JSONObject(objectStr);

		} catch (Exception e) {
			e.printStackTrace();
			String ss = "地區";
			ss += areaid + ":";
			ss += "發生錯誤2,";
			sb.append(ss);
			logger.error(ss, e);
		}

		return objectStr;
	}

	/**
	 * 更新一批城市的預測資料
	 * 
	 * @param host
	 * @param etlDate
	 * @param sb
	 * @return false:至少有一個城市更新時出現錯誤
	 */
	private boolean apiInsertWEATHER_DATA(String host, String etlDate,
			StringBuffer sb) {
		boolean bAll = true;

		logger.info("目標 " + codeList.length() + " 個城市");
		for (int i = 0; i < codeList.length() && i < getSyncCities(); i++) {
			boolean b = false;
			try {
				JSONObject jSONObject = codeList.getJSONObject(i);
				String areaid = jSONObject.getString("areaid");

				String key = "apiWData_" + etlDate + "_" + areaid;

				DailyWeather dailyWeather = dailyWeatherRepository.findOne(key);
				String objectStr = dailyWeather.getJsonData();
				if (objectStr == null) {
					logger.error(etlDate + "沒有" + areaid);
					continue;
				}
				b = apiInsertWEATHER_DATA(host, areaid, objectStr);
			} catch (JSONException ex) {
				ex.printStackTrace();
			} finally {
				bAll &= b;
			}

		}
		return bAll;
	}

	/**
	 * 更新一批城市的15日預測資料
	 * 
	 * @param host
	 * @param etlDate
	 * @param sb
	 * @return false:至少有一個城市更新時出現錯誤
	 */
	private boolean apiInsertWEATHER_DATA_PRE15(String host, String etlDate,
			StringBuffer sb) {
		boolean bAll = true;

		logger.info("目標 " + codeList.length() + " 個城市");
		for (int i = 0; i < codeList.length() && i < getSyncCities(); i++) {
			boolean b = false;
			try {
				JSONObject jSONObject = codeList.getJSONObject(i);
				String areaid = jSONObject.getString("areaid");

				String key = "apiWD15_" + etlDate + "_" + areaid;
				DailyWeather dailyWeather = dailyWeatherRepository.findOne(key);
				String objectStr = dailyWeather.getJsonData();

				if (objectStr == null) {
					logger.error(etlDate + "沒有" + areaid);
					continue;
				}
				b = apiInsertWEATHER_DATA_PRE15(host, areaid, objectStr);

			} catch (JSONException ex) {
				ex.printStackTrace();
				return false;
			} finally {
				bAll &= b;
			}

		}
		return bAll;
	}

	/*
	 * 
	 * 透過apiEXEC_SP執行特定環境的每日例行SP
	 * 
	 * return true正確 false過程有誤
	 */
	private boolean apiEXEC_SP(String host, String etlDate, StringBuffer sb) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiEXEC_SP/" + this.etl_date;
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);
			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("403");
				b = false;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			logger.info(objectStr);
			JSONObject jsonObject = new JSONObject(objectStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("EXEC SP for " + host + " result:" + b);
			return b;
		}
	}

	/*
	 * 
	 * 透過apiInsertWEATHER_DATA更新特定環境特定城市預測資料
	 * 
	 * return true正確 false過程有誤
	 */
	private boolean apiInsertWEATHER_DATA(String host, String areaid0,
			String objectStr) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiInsertWEATHER_DATA";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		HttpEntity httpEntity = null;

		List<NameValuePair> form = new ArrayList<>();
		form.add(new BasicNameValuePair("etlDate", this.etl_date));
		form.add(new BasicNameValuePair("areaid0", areaid0));
		form.add(new BasicNameValuePair("objectStr", objectStr));
		int sum = 0;
		try {

			HttpResponse response = HttpUtils.doPost(host, path, method,
					headers, querys, form, "");

			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("403");
				b = false;
			} else if (statusLine.getStatusCode() == 404) {
				logger.error("404");
				b = false;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			JSONObject jsonObject = new JSONObject(objectStr);
			b = (boolean) jsonObject.get("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			b = false;
		} finally {
			logger.info("api Insert WEATHER_DATA to " + host + " result:" + b);
			return b;
		}
	}

	/*
	 * 
	 * 透過apiInsertWEATHER_DATA_PRe15更新特定環境特定城市預測資料
	 * 
	 * return true正確 false過程有誤
	 */
	private boolean apiInsertWEATHER_DATA_PRE15(String host, String areaid,
			String objectStr) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiInsertWEATHER_DATA_PRE15";
		String method = "GET";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		HttpEntity httpEntity = null;

		List<NameValuePair> form = new ArrayList<>();
		form.add(new BasicNameValuePair("etlDate", this.etl_date));
		// form.add(new BasicNameValuePair("areaid0", areaid));
		form.add(new BasicNameValuePair("objectStr", objectStr));
		int sum = 0;
		try {

			HttpResponse response = HttpUtils.doPost(host, path, method,
					headers, querys, form, "");

			logger.debug(response.toString());
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 403) {
				logger.error("403");
				b = false;
			} else if (statusLine.getStatusCode() == 404) {
				logger.error("404");
				b = false;
			}
			// 获取response的body
			httpEntity = response.getEntity();
			objectStr = EntityUtils.toString(httpEntity);
			JSONObject jsonObject = new JSONObject(objectStr);
			b = (boolean) jsonObject.get("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			b = false;
		} finally {
			logger.info("api Insert WEATHER_DATA_PRE15 to " + host + " result:"
					+ b);
			return b;
		}
	}

	/**
	 * 手動功能測試 用讀取的方式來確認系統正常運作中
	 */
	public List takeWeatherDate2DBtest() {
		this.etl_date = DateUtil.acquireEtlDate(0);

		List list = new ArrayList();
		String syncServer = getSyncServer();
		list.add("syncCityList: " + getSyncCityList());// 系統內定的讀取城市資料環境來源
		list.add("syncCities: " + String.valueOf(getSyncCities()));// 系統內定的執行同步城市數
		list.add("syncEnable: " + String.valueOf(isSyncEnable()));// 系統內定的同步功能啟動
		list.add("syncServer: " + syncServer);// 系統內定的執行環境群
		list.add("syncUrl1: " + getSyncUrl1());// 系統內定的執行環境1
		list.add("syncUrl2: " + getSyncUrl2());// 系統內定的執行環境2
		list.add("syncUrl3: " + getSyncUrl3());// 系統內定的執行環境3

		// if (isSyncEnable()) {
		// if (StringUtils.indexOf(syncServer, "prod") >= 0) {
		// updateEtlDatefor(getSyncUrl1());// 更新ETL基準日Prod
		// }
		// if (StringUtils.indexOf(syncServer, "staging") >= 0) {
		// updateEtlDatefor(getSyncUrl2());// 更新ETL基準日dev
		// }
		// if (StringUtils.indexOf(syncServer, "dev") >= 0) {
		// updateEtlDatefor(getSyncUrl3());// 更新ETL基準日dev
		// }
		// } else {
		// logger.warn("同步不執行,syncEnable: " + isSyncEnable());
		// }
		String cityListSrc = takeSrc(getSyncCityList());
		list.add("cityListSrc: " + cityListSrc);
		boolean b = obtainCityIds(cityListSrc);
		list.add("obtainCityIds: " + String.valueOf(b));
		list.add("obtainCitys: " + String.valueOf(codeList.length()));
		String checkCityDataExistError = mession6test();
		list.add("checkCityDataExistError: " + checkCityDataExistError);
		// return;
		// if (!mession7())
		// return;
		//
		// if (isSyncEnable()) {
		// if (StringUtils.indexOf(syncServer, "prod") >= 0) {
		// syncServer(getSyncUrl1());// 開始同步Prod資料
		// }
		// if (StringUtils.indexOf(syncServer, "staging") >= 0) {
		// syncServer(getSyncUrl2());// 開始同步Staging資料
		// }
		// if (StringUtils.indexOf(syncServer, "dev") >= 0) {
		// syncServer(getSyncUrl3());// 開始同步Dev資料
		// }
		// }

		return list;
	}
}
