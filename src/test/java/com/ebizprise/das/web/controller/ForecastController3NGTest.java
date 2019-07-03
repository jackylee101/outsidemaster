package com.ebizprise.das.web.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.mortbay.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ebizprise.das.utils.DateUtil;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.prive.utils.GoogleSheetUtil;

import antlr.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 單品項預測 大單元測試
 * 
 * @author jacky.lee
 *
 */
// @ActiveProfiles("dev")
@Slf4j
public class ForecastController3NGTest extends ForecastControllerNGTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void pace_daywork() {
		Date today = new Date();
		Date yesterday = DateUtil.add(today, -1);
		String targetDate = DateUtil.date2str(yesterday, "yyyy-MM-dd");

		// targetDate = "2019-06-25";
		logger.info("target Date:" + targetDate);
		pace_Price_excel(targetDate);
		pace_NAV_everyday(targetDate);
	}

	@Test
	public void pace_daywork2() {
		Date today = new Date();
		Date yesterday = DateUtil.add(today, -1);
		String targetDate = DateUtil.date2str(yesterday, "yyyy-MM-dd");

		// targetDate = "2019-06-25";
		logger.info("target Date:" + targetDate);
//		pace_Price_excel(targetDate);
		pace_NAV_everyday(targetDate);
	}

	@Test
	public void pace_googleapi() {
		String targetDate = "6/30/2019";
		List<List<Object>> values = readSheet();
		if (!examSheetTargetDateExist(targetDate, values)) {
			String[] ssValue = new String[] {};
			appendTargetDateData(ssValue);
		}
	}

	private boolean examSheetTargetDateExist(String targetDate, List<List<Object>> values) {
		List<Object> lastlist = values.get(values.size() - 1);
		String eDate = (String) lastlist.get(0);
		if (targetDate.equals(eDate))
			return true;
		else
			return false;
	}

	private void appendTargetDateData(String[] sa) {
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

			String spreadsheetId = "1vAvvm3LCy11SseI-En5q-2Rh5OddR00_lwMrBVhVKEw";
			String sheetName = "Nutmeg-MP(USD)";
			String startCell = "A1";

			String[][] ssValue = new String[][] { sa };

			GoogleSheetUtil.AppendRow(spreadsheetId, sheetName, startCell, ssValue);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeSheet() {
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

			String spreadsheetId = "1xGj6-ly-PwVoD1AlZ3-dWcM_PzF2kUjQu7nJoBPc6LY";
			String sheetName = "首發頁";
//			Report r = new Report();
//			GoogleSheetUtil.writeTest(r, spreadsheetId, range);
//			GoogleSheetUtil.deleteTest(spreadsheetId);

			String[][] ssValue = new String[][] { { "Item", "Cost", "Stocked", "Ship Date" },
					{ "Wheel", "$20.50", "4", "3/1/2016" }, { "Door", "$15", "2", "3/15/2016" },
					{ "Engine", "$100", "1", "30/20/2016" },
					{ "Totals", "=SUM(B2:B4)", "=SUM(C2:C4)", "=MAX(D2:D4)" } };

			GoogleSheetUtil.AppendRow(spreadsheetId, sheetName, "E3", ssValue);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<List<Object>> readSheet() {
		List<List<Object>> values = null;
		try {
			NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

			String spreadsheetId = "1vAvvm3LCy11SseI-En5q-2Rh5OddR00_lwMrBVhVKEw";
			String sheetName = "Nutmeg-MP(USD)";
			String readRange = "A1:E";
			String range = sheetName + "!" + readRange;
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, GoogleSheetUtil.JSON_FACTORY,
					GoogleSheetUtil.getCredentials(HTTP_TRANSPORT)).setApplicationName(GoogleSheetUtil.APPLICATION_NAME)
							.build();
			ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			values = response.getValues();
			if (values == null || values.isEmpty()) {
				logger.error("No data found.");
			} else {
				logger.info("size: " + values.size());
				for (List row : values) {
					logger.info(row.get(0) + " " + row.get(1));
				}
			}

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return values;
		}

	}

	public void pace_Price_excel(String targetDate) {
		FPUtil.realHost = "https://tw-api-micro.privemanagers.com";
		// FPUtil.realHost = "http://localhost:8075";
		// FPUtil.realHost = "http://uatrds.rollingdemand.com.cn";
		try {
			Thread.sleep((int) (Math.random() * 3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int n = getWhich();
		String from = "2019-06-12";
		// String to = "2019-06-26";

		logger.warn(String.valueOf(n));
		paceE0T(user1[n][0], user1[n][1], from, targetDate);
		paceE1T(user1[n][0], user1[n][1], from, targetDate);
		paceE2T(user1[n][0], user1[n][1], from, targetDate);
		paceE3T(user1[n][0], user1[n][1], from, targetDate);
		paceE4T(user1[n][0], user1[n][1], from, targetDate);
	}

	public void pace_NAV_everyday(String targetDate) {
		FPUtil.realHost = "https://tw-api-micro.privemanagers.com";
		String[] sa = paceE0A(user1[0][0], user1[0][1], targetDate);

		String itargetDate = sa[0];
		List<List<Object>> values = readSheet();
		if (!examSheetTargetDateExist(itargetDate, values)) {
			appendTargetDateData(sa);
		}
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
