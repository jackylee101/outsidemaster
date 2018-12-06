package com.ebizprise.das.scheduled.service.weather;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherSyncTest {

	private static final Log logger = LogFactory.getLog(WeatherSyncTest.class);

	@Autowired
	private WeatherSync weatherSync;

//	@Test
	public void tasks() {
		weatherSync.takeWeatherDate2DB();
		Assume.assumeTrue(true);
	}

}
