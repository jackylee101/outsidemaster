package com.ebizprise.das.web.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebizprise.das.scheduled.service.weather.WeatherSync;

/**
 * description:
 * 
 * @author Jacky
 * @date 21/12/2018 18:20 PM
 * @email jacky.lee@ebizprise.com
 */

@Controller
@RequestMapping("/")
public class WeatherSyncController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeatherSyncController.class);

	@Autowired
	private WeatherSync weatherSync;

	@RequestMapping("/takeWeatherDate2DB")
	public @ResponseBody Map<String, String> takeWeatherDate2DB(
			@RequestParam(value = "etlDate", required = false) String etlDate,
			@RequestParam(value = "syncServer", required = false) String syncServer) {
		Map<String, String> map = new HashMap<String, String>(16);

		weatherSync.takeWeatherDate2DB(etlDate, syncServer);
		return map;
	}

	@RequestMapping("/takeWeatherDate2DBtest")
	public @ResponseBody List takeWeatherDate2DBtest(
			@RequestParam(value = "etlDate", required = false) String etlDate,
			@RequestParam(value = "syncServer", required = false) String syncServer) {
		List list = weatherSync.takeWeatherDate2DBtest(etlDate, syncServer);
		return list;
	}

}
