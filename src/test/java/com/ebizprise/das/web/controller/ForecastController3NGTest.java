package com.ebizprise.das.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ebizprise.das.web.controller.minor.FPUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 單品項預測 大單元測試
 * 
 * @author jacky.lee
 *
 */
//@ActiveProfiles("dev")
@Slf4j
public class ForecastController3NGTest extends ForecastControllerNGTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void pace_Price_excel() {
		FPUtil.realHost = "https://tw-api-micro.privemanagers.com";
		// FPUtil.realHost = "http://localhost:8075";
		// FPUtil.realHost = "http://uatrds.rollingdemand.com.cn";
		try {
			Thread.sleep((int) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int n = getWhich();
		String from = "2019-06-03";
		String to = "2019-06-18";

		logger.warn(String.valueOf(n));
		paceE0T(user1[n][0], user1[n][1], from, to);
		paceE1T(user1[n][0], user1[n][1], from, to);
		paceE2T(user1[n][0], user1[n][1], from, to);
		paceE3T(user1[n][0], user1[n][1], from, to);
		paceE4T(user1[n][0], user1[n][1], from, to);
	}

	@Test
	public void pace_NAV_everyday() {
		FPUtil.realHost = "https://tw-api-micro.privemanagers.com";

		paceE0A(user1[0][0], user1[0][1]);
	}

	@Test
	public void pace_bloomberg_PRICE_INDEX() {
		String csv = "bloomberg_PRICE_INDEX3364948632092236085";
		paceE0S(csv);
	}

	@Test
	public void pace_Backtesting() {
		FPUtil.realHost = "https://tw-api-micro.privemanagers.com";
		// FPUtil.realHost = "http://localhost:8075";
		// FPUtil.realHost = "http://uatrds.rollingdemand.com.cn";
		try {
			Thread.sleep((int) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int n = getWhich();
		logger.warn(String.valueOf(n));
		String from = "2015-03-15";
		String to = "2019-05-16";
		paceE11T(user1[n][0], user1[n][1], from, to);
	}
}
