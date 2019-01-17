package com.ebizprise.das.scheduled.service.weather.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ebizprise.das.WebApplication;
import com.ebizprise.das.db.entity.DailyWeather;
import com.ebizprise.das.db.entity.WeatherLocationMap;
import com.ebizprise.das.db.repository.DailyWeatherRepository;
import com.ebizprise.das.db.repository.WeatherLocationMapRepository;
import com.ebizprise.das.scheduled.service.weather.WeatherSync;
import com.ebizprise.das.utils.CommonUtils;
import com.ebizprise.das.utils.DateUtil;
import com.ebizprise.das.utils.HttpUtils452;

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
	// private String etl_date;
	// private JSONArray cityList;

	@Autowired
	ConfigurableEnvironment env;

	// @Autowired
	// private OutsideConfig outsideConfig;
	//
	@Autowired
	private WeatherLocationMapRepository weatherLocationMapRepository;

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
		if (etlDate == null)
			etlDate = DateUtil.acquireEtlDate(0);

		if (syncServer == null)// 手動如果沒有指定環境
			syncServer = "dev";// 就只會讓dev環境執行同步更新

		if (isSyncEnable()) {
			if (StringUtils.indexOf(syncServer, "prod") >= 0) {
				updateEtlDatefor(etlDate, getSyncUrl1());// 更新ETL基準日Prod
			}
			if (StringUtils.indexOf(syncServer, "staging") >= 0) {
				updateEtlDatefor(etlDate, getSyncUrl2());// 更新ETL基準日dev
			}
			if (StringUtils.indexOf(syncServer, "dev") >= 0) {
				updateEtlDatefor(etlDate, getSyncUrl3());// 更新ETL基準日dev
			}
		} else {
			logger.warn("同步不執行,syncEnable: " + isSyncEnable());
		}
		String cityListSrc = takeSrc(getSyncCityList());

		List<WeatherLocationMap> cityList = obtainCityIds(cityListSrc);

		if (!mession6(cityList, etlDate))
			return;
		if (!mession7(cityList, etlDate))
			return;

		if (isSyncEnable()) {
			if (StringUtils.indexOf(syncServer, "prod") >= 0) {
				syncServer(cityList, etlDate, getSyncUrl1());// 開始同步Prod資料
			}
			if (StringUtils.indexOf(syncServer, "staging") >= 0) {
				syncServer(cityList, etlDate, getSyncUrl2());// 開始同步Staging資料
			}
			if (StringUtils.indexOf(syncServer, "dev") >= 0) {
				syncServer(cityList, etlDate, getSyncUrl3());// 開始同步Dev資料
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
	private boolean updateEtlDatefor(String etlDate, String host) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiUpdateETL_date/" + etlDate;
		String method = "GET";
		// Map<String, String> headers = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			objectStr = HttpUtils452.doGet(host + path, params, 50000);

			logger.debug(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
			boolean success = (boolean) jsonObject.get("success");
			if (success) {
			} else {
				String msgDesc = (String) jsonObject.get("error");
				logger.error(msgDesc);
				b = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("Update ETL date for " + host + " result:" + b);
			return b;
		}

	}

	/*
	 * 
	 * 取得指定城市
	 */
	protected List<WeatherLocationMap> obtainCityIds(String host) {
		List<WeatherLocationMap> list1 = weatherLocationMapRepository.findAll();

		List<WeatherLocationMap> list2 = obtainCityIds1(host);
		if (list2 == null) {
			return list1;
		}

		if (list2.size() > list1.size()) {
			weatherLocationMapRepository.deleteAll();
			weatherLocationMapRepository.saveAll(list2);
			return list2;
		}
		if (list2.size() == list1.size()) {
			return list2;
		} else {
			return list1;
		}

	}

	/*
	 * 
	 * 由ShowAPI取得指定城市
	 */
	protected List obtainCityIds1(String host) {
		String path = CommonUtils.PRIFIX + "/apiSelectAPI_City_ID_Mapping";
		String method = "GET";
		Map<String, Object> params = new HashMap<String, Object>();
		HttpEntity httpEntity = null;

		int sum = 0;
		List cityList2 = new ArrayList();
		try {
			String objectStr = HttpUtils452.doGet(host + path, params, 50000);
			// 获取response的body
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
			JSONArray codeList = (JSONArray) jsonObject.get("codeList");
			// JSONObject hsin = cityList.getJSONObject(307);
			// cityList = new JSONArray();
			// cityList.put(0, hsin);
			String msgCode = (String) jsonObject.get("msgCode");
			if ("error".equals(msgCode)) {
				String msgDesc = (String) jsonObject.get("msgDesc");
				logger.error(msgDesc);
			} else {
				for (int i = 0; i < codeList.size(); i++) {
					JSONObject city = codeList.getJSONObject(i);

					String createDate = (String) city.get("createDate");
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					Date createDate1 = new Date();
					try {
						createDate1 = formatter.parse(createDate);
					} catch (Exception e) {
					}
					city.put("createDate", createDate1);

					WeatherLocationMap weatherLocationMap = new WeatherLocationMap();
					BeanUtils.copyProperties(weatherLocationMap, city);
					cityList2.add(weatherLocationMap);
				}
				logger.debug(objectStr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("api SelectAPI City_ID_Mapping from " + host
					+ " result:" + cityList2.size());
			return cityList2;
		}
	}

	/*
	 * 
	 * 指定城市321之一
	 */
	private boolean mession6(List<WeatherLocationMap> cityList, String etlDate) {
		int sum = api3rdWData(cityList, etlDate, new StringBuffer());
		// if (sum == 0)
		// return false;
		return true;
	}

	/*
	 * 
	 * 指定城市320之一 如果出現exception(最常見是redis忘了開) 會把訊息印出
	 */
	private String mession6test(List<WeatherLocationMap> cityList,
			String etlDate) {
		StringBuffer sb = new StringBuffer();
		int sum = api3rdWData_test(cityList, etlDate, sb);
		if (sum == -1)
			return sb.toString();
		return "";
	}

	/*
	 * 
	 * 指定城市321之2
	 */
	private boolean mession7(List<WeatherLocationMap> cityList, String etlDate) {
		int sum = api3rdWD15(cityList, etlDate, new StringBuffer());
		return true;
	}

	/*
	 * 
	 * 開始同步Prod資料
	 */
	private boolean syncServer(List<WeatherLocationMap> cityList,
			String etlDate, String syncUrl) {
		boolean b1 = mession9(cityList, etlDate, syncUrl);
		if (!b1)
			logger.error("七日預測數據同步有誤: " + syncUrl);

		boolean b2 = mession10(cityList, etlDate, syncUrl);
		if (!b2)
			logger.error("15日預測數據同步有誤: " + syncUrl);

		boolean b3 = mession11(etlDate, syncUrl);
		if (!b3)
			logger.error("呼叫sp有誤: " + syncUrl);
		return b3;
	}

	/*
	 * 
	 * 開始同步Prod資料
	 */
	private boolean syncServerTest(String etlDate, String syncUrl) {
		// boolean b1 = mession9(syncUrl);
		// if (!b1)
		// logger.error("七日預測數據同步有誤: " + syncUrl);
		//
		// boolean b2 = mession10(syncUrl);
		// if (!b2)
		// logger.error("15日預測數據同步有誤: " + syncUrl);

		boolean b3 = mession99(etlDate, syncUrl);
		if (!b3)
			logger.error("呼叫sp有誤: " + syncUrl);
		return b3;
	}

	/*
	 * 
	 * 觸發321城市資料填充之1
	 */
	private boolean mession9(List<WeatherLocationMap> cityList, String etlDate,
			String host) {
		return apiInsertWEATHER_DATA(cityList, host, etlDate,
				new StringBuffer());
	}

	/*
	 * 
	 * 觸發321城市資料填充之2
	 */
	private boolean mession10(List<WeatherLocationMap> cityList,
			String etlDate, String host) {
		return apiInsertWEATHER_DATA_PRE15(cityList, host, etlDate,
				new StringBuffer());
	}

	/*
	 * 
	 * 觸發SP
	 */
	private boolean mession11(String etlDate, String host) {
		return apiEXEC_SP(host, etlDate, new StringBuffer());
	}

	/*
	 * 
	 * 觸發測試SP
	 */
	private boolean mession99(String etlDate, String host) {
		return apiEXEC_TEST(host, etlDate, new StringBuffer());
	}

	/**
	 * 對320個城市個別去查七日天氣預測
	 * 
	 * @param etlDate
	 * @param sb
	 * @return
	 */
	private Integer api3rdWData(List<WeatherLocationMap> cityList,
			String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + cityList.size() + " 個城市");
		for (int i = 0; i < cityList.size() && i < getSyncCities(); i++) {
			JSONObject jSONObject;
			try {
				WeatherLocationMap weatherLocationMap = cityList.get(i);
				String c11 = weatherLocationMap.getC11();
				String c12 = weatherLocationMap.getC12();
				String areaid = weatherLocationMap.getAreaid();
				String area = weatherLocationMap.getArea();
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

					objectStr = changeCityInfo(objectStr, weatherLocationMap);

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
	private Integer api3rdWData_test(List<WeatherLocationMap> cityList,
			String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + cityList.size() + " 個城市");
		for (int i = 0; i < cityList.size() && i < getSyncCities(); i++) {
			try {
				WeatherLocationMap weatherLocationMap = cityList.get(i);
				String c11 = weatherLocationMap.getC11();
				String c12 = weatherLocationMap.getC12();
				String areaid = weatherLocationMap.getAreaid();
				String area = weatherLocationMap.getArea();
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
	private String changeCityInfo(String objectStr,
			WeatherLocationMap weatherLocationMap) {
		JSONObject jsonObject2 = null;
		JSONObject showapi_res_body = null;
		JSONObject cityInfo = null;
		String robjectStr = objectStr;
		try {
			jsonObject2 = JSONObject.parseObject(objectStr);
			showapi_res_body = jsonObject2.getJSONObject("showapi_res_body");
			cityInfo = showapi_res_body.getJSONObject("cityInfo");

			String areaid2 = cityInfo.getString("c1");
			String areaid1 = weatherLocationMap.getAreaid();

			if (!areaid1.equals(areaid2)) {
				String c1 = weatherLocationMap.getC1();
				String c2 = weatherLocationMap.getC2();
				String c3 = weatherLocationMap.getC3();
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
	private Integer api3rdWD15(List<WeatherLocationMap> cityList,
			String etlDate, StringBuffer sb) {
		int sum = 0;

		logger.info("目標 " + cityList.size() + " 個城市");
		for (int i = 0; i < cityList.size() && i < getSyncCities(); i++) {
			try {
				WeatherLocationMap weatherLocationMap = cityList.get(i);
				String areaid = weatherLocationMap.getAreaid();
				String c11 = weatherLocationMap.getC11();
				String c12 = weatherLocationMap.getC12();
				String area = weatherLocationMap.getArea();
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

	// private boolean checkNowExist(String key, String id) throws Exception {
	// String myMap2 = redisRepository.findMyMap(key, id);
	// if (myMap2 == null)
	// return false;
	// else
	// return true;
	// }
	/**
	 * 從H2.dailyWeatherRepository檢查天氣資料是否已存在
	 * 
	 * @param key
	 * @param id
	 * @return
	 */
	private boolean checkNowExist(String key) throws Exception {
		Optional<DailyWeather> dailyWeatherOpt = dailyWeatherRepository
				.findById(key);
		DailyWeather dailyWeather = dailyWeatherOpt.orElse(null);
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
		Map<String, Object> headers = new HashMap<String, Object>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("need3HourForcast", "0");
		params.put("needAlarm", "1");
		params.put("needHourData", "0");
		params.put("needIndex", "1");
		params.put("needMoreDay", "1");
		params.put("phone_code", phone_code);
		params.put("post_code", post_code);

		String objectStr = null;
		try {
			objectStr = HttpUtils452.doGet(host + path, headers, params, 50000,
					false);
			logger.debug(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
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
		Map<String, Object> headers = new HashMap<String, Object>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, Object> params = new HashMap<String, Object>();
		// querys.put("area", area);
		params.put("areaid", areaid);
		HttpEntity httpEntity = null;

		String objectStr = null;
		try {
			objectStr = HttpUtils452.doGet(host + path, headers, params, 50000,
					false);

			logger.debug(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);

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
	private boolean apiInsertWEATHER_DATA(List<WeatherLocationMap> cityList,
			String host, String etlDate, StringBuffer sb) {
		boolean bAll = true;

		logger.info("目標 " + cityList.size() + " 個城市");
		for (int i = 0; i < cityList.size() && i < getSyncCities(); i++) {
			boolean b = false;
			try {
				WeatherLocationMap weatherLocationMap = cityList.get(i);
				String areaid = weatherLocationMap.getAreaid();

				String key = "apiWData_" + etlDate + "_" + areaid;
				Optional<DailyWeather> dailyWeatherOpt = dailyWeatherRepository
						.findById(key);
				DailyWeather dailyWeather = dailyWeatherOpt.orElse(null);

				if (dailyWeather != null) {
					String objectStr = dailyWeather.getJsonData();
					b = apiInsertWEATHER_DATA(etlDate, host, areaid, objectStr);
				} else {
					logger.error(etlDate + "沒有" + areaid);
					continue;
				}
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
	private boolean apiInsertWEATHER_DATA_PRE15(
			List<WeatherLocationMap> cityList, String host, String etlDate,
			StringBuffer sb) {
		boolean bAll = true;

		logger.info("目標 " + cityList.size() + " 個城市");
		for (int i = 0; i < cityList.size() && i < getSyncCities(); i++) {
			boolean b = false;
			try {
				WeatherLocationMap weatherLocationMap = cityList.get(i);
				String areaid = weatherLocationMap.getAreaid();

				String key = "apiWD15_" + etlDate + "_" + areaid;
				Optional<DailyWeather> dailyWeatherOpt = dailyWeatherRepository
						.findById(key);
				DailyWeather dailyWeather = dailyWeatherOpt.orElse(null);

				if (dailyWeather != null) {
					String objectStr = dailyWeather.getJsonData();
					b = apiInsertWEATHER_DATA_PRE15(etlDate, host, areaid,
							objectStr);
				} else {
					logger.error(etlDate + "沒有" + areaid);
					continue;
				}
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
		String path = CommonUtils.PRIFIX + "/apiEXEC_SP/" + etlDate;
		String method = "GET";
		Map<String, Object> params = new HashMap<String, Object>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			logger.info("EXEC SP for " + host + " start...");
			objectStr = HttpUtils452.doGet(host + path, params, 50 * 60 * 1000);

			logger.info(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
			b = (boolean) jsonObject.get("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			logger.info("EXEC SP for " + host + " result:" + b);
			return b;
		}
	}

	/*
	 * 
	 * 透過apiEXEC_SP執行特定環境的每日例行SP
	 * 
	 * return true正確 false過程有誤
	 */
	private boolean apiEXEC_TEST(String host, String etlDate, StringBuffer sb) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiEXEC_TEST/" + etlDate;
		String method = "GET";
		Map<String, Object> params = new HashMap<String, Object>();
		HttpEntity httpEntity = null;

		int sum = 0;
		String objectStr;
		try {
			logger.info("EXEC testSP for " + host + " start...");
			objectStr = HttpUtils452.doGet(host + path, params, 50 * 60 * 1000);

			logger.info(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
			b = (boolean) jsonObject.get("success");
		} catch (Exception ex) {
			b = false;
			ex.printStackTrace();
		} finally {
			logger.info("EXEC testSP for " + host + " result:" + b);
			return b;
		}
	}

	/*
	 * 
	 * 透過apiInsertWEATHER_DATA更新特定環境特定城市預測資料
	 * 
	 * return true正確 false過程有誤
	 */
	private boolean apiInsertWEATHER_DATA(String etlDate, String host,
			String areaid0, String objectStr) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiInsertWEATHER_DATA";
		String method = "GET";
		Map<String, Object> headers = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("etlDate", etlDate);
		params.put("areaid0", areaid0);
		params.put("objectStr", objectStr);

		HttpEntity httpEntity = null;

		int sum = 0;
		try {
			objectStr = HttpUtils452.doPost(host + path, headers, params,
					50000, false);

			logger.debug(objectStr);
			JSONObject jsonObject = JSONObject.parseObject(objectStr);
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
	private boolean apiInsertWEATHER_DATA_PRE15(String etlDate, String host,
			String areaid, String objectStr) {
		boolean b = true;
		String path = CommonUtils.PRIFIX + "/apiInsertWEATHER_DATA_PRE15";
		String method = "GET";
		Map<String, Object> headers = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("etlDate", etlDate);
		// params.put("areaid0", areaid);
		params.put("objectStr", objectStr);

		HttpEntity httpEntity = null;

		int sum = 0;
		try {
			objectStr = HttpUtils452.doPost(host + path, headers, params,
					50000, false);

			JSONObject jsonObject = JSONObject.parseObject(objectStr);
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
	public List takeWeatherDate2DBtest(String etlDate, String syncServer) {
		if (etlDate == null)
			etlDate = DateUtil.acquireEtlDate(0);

		if (syncServer == null)
			syncServer = getSyncServer();

		List list = new ArrayList();

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
		List<WeatherLocationMap> cityList = obtainCityIds(cityListSrc);
		// list.add("obtainCityIds: " + String.valueOf(b));
		list.add("obtainCitys: " + cityList.size());
		String checkCityDataExistError = mession6test(cityList, etlDate);
		list.add("checkCityDataExistError: " + checkCityDataExistError);
		// return;
		// if (!mession7())
		// return;
		//
		if (isSyncEnable()) {
			if (StringUtils.indexOf(syncServer, "prod") >= 0) {
				list.add("check Prod Start: ");
				boolean b1 = syncServerTest(etlDate, getSyncUrl1());// 開始同步Prod資料
				list.add("check Prod end: " + b1);
			}
			if (StringUtils.indexOf(syncServer, "staging") >= 0) {
				list.add("check Uat Start: ");
				boolean b1 = syncServerTest(etlDate, getSyncUrl2());// 開始同步Staging資料
				list.add("check Uat end: " + b1);
			}
			if (StringUtils.indexOf(syncServer, "dev") >= 0) {
				list.add("check Dev Start: ");
				boolean b1 = syncServerTest(etlDate, getSyncUrl3());// 開始同步Dev資料
				list.add("check Dev end: " + b1);
			}
		}

		return list;
	}
}
