package com.ebizprise.das.scheduled.service.weather.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ebizprise.das.WebApplication;
import com.ebizprise.das.config.OutsideConfig;
import com.ebizprise.das.db.model.MyMap;
import com.ebizprise.das.db.repository.RedisRepository;
import com.ebizprise.das.scheduled.service.weather.WeatherSync;
import com.ebizprise.das.scheduled.service.weather.impl.WeatherSyncImpl;
import com.ebizprise.das.utils.CommonUtils;
import com.ebizprise.das.utils.DateUtil;
//import com.ebizprise.das.utils.HttpUtils;
import com.ebizprise.das.utilsweb.common.CodeUtilsCommon;

//import com.ebizprise.das.web.service.WeatherService;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
public class WeatherSyncTest extends WeatherSyncImpl {
	private static final Logger logger = LoggerFactory
			.getLogger(WeatherSyncTest.class);

	@Test
	public void testGetFirstDateAndLastDateByMonth() {
		String apiUrl = "http://192.168.128.23:8075";
		List list=obtainCityIds(apiUrl);
		// Assert.assertEquals(defaultFirstDate,
		// firstDateAndLastDateByMonth.getFirstDate());
		// Assert.assertEquals(defaultLastDate,
		// firstDateAndLastDateByMonth.getLastDate());
	}
}
