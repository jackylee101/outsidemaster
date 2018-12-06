package com.ebizprise.das.web.controller.v1;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.utils.CommonUtils;
import com.google.common.collect.Maps;

public class WeatherSyncControllerTest extends PaceBase {

	private static final Log logger = LogFactory
			.getLog(WeatherSyncControllerTest.class);

	@Test
	public void signin1() {
		String url = CommonUtils.PRIFIX + "/takeWeatherDate2DBtest/";
		Map<String, String> params = Maps.newHashMap();

		ResponseEntity<String> response = paceType(url, HttpMethod.GET, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

//		return queryForm;
	}

}
