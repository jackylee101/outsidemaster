package com.ebizprise.das.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	private static final Log logger = LogFactory.getLog(DateUtil.class);

	protected static Map<String, String> ymdPatterns = new HashMap<String, String>();
	protected static Map<String, String> fullPatterns = new HashMap<String, String>();

	protected static Map<String, String> keywords = new HashMap<String, String>();

	private static SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

	static {
		ymdPatterns.put("\\d{4}/\\d{2}/\\d{2}", "yyyy/MM/dd");
		ymdPatterns.put("\\d{4}-\\d{2}-\\d{2}", "yyyy-MM-dd");
		ymdPatterns.put("\\d{4}\\d{2}\\d{2}", "yyyyMMdd");

		fullPatterns.put("\\d{4}/\\d{2}/\\d{2}", "yyyy/MM/dd");
		fullPatterns.put("\\d{4}-\\d{2}-\\d{2}", "yyyy-MM-dd");
		fullPatterns.put("\\d{4}\\d{2}", "yyyyMM");
		fullPatterns.put("\\d{4}\\d{1}", "yyyyM");
		fullPatterns.put("\\d{4}\\d{2}\\d{2}", "yyyyMMdd");
		fullPatterns.put("\\d{4}\\d{1}\\d{1,2}", "yyyyMd");

		keywords.put("年", "");
		keywords.put("月", "");
		keywords.put("日", "");

		keywords.put("Jan", "01");
		keywords.put("Feb", "02");
		keywords.put("Mar", "03");
		keywords.put("Apr", "04");
		keywords.put("May", "05");
		keywords.put("Jun", "06");
		keywords.put("Jul", "07");
		keywords.put("Aug", "08");
		keywords.put("Sep", "09");
		keywords.put("Oct", "10");
		keywords.put("Nov", "11");
		keywords.put("Dec", "12");
	}

	/**
	 * <pre>
	 * Format date string to same date format (yyyyMMdd).
	 * PS: if not date, will do no thing
	 * </pre>
	 * 
	 * @param txt
	 * @return
	 * @throws Exception
	 */
	public static String toValidDate(String txt) {
		// PS: requirement is don't convert ym to ymd
		return toValidDate(txt, false);
	}

	public static String toValidDate(String txt, boolean convertYmToYmd) {
		// quick check
		if (StringUtils.isBlank(txt)) {
			logger.debug("not match: " + txt);
			return "";
		}
		if (txt.length() < 5) {
			logger.debug("not match: " + txt);
			return txt;
		}

		// handle keywords
		AtomicReference<String> tmpTxt = new AtomicReference<>(txt);
		keywords.forEach((k, v) -> {
			if (tmpTxt.get().indexOf(k) > -1) {
				tmpTxt.set(tmpTxt.get().replaceAll(k, v));
			}
		});
		String newText = tmpTxt.get();

		// handle others
		if (newText.indexOf(" ") > -1) {
			newText = newText.split(" ")[0];
		}
		if (newText.indexOf("T") > -1) {
			newText = newText.split("T")[0];
		}

		try {
			// check by RegExp
			Object[] result = null;
			if (convertYmToYmd) {
				result = isMatch(fullPatterns, newText);
			} else {
				result = isMatch(ymdPatterns, newText);
			}

			Boolean match = (Boolean) result[0];
			String format = (String) result[1];

			if (match) {
				// format to same format
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				Date date = formatter.parse(newText);
				newText = outputFormat.format(date);
				logger.debug("match: " + txt + " -> " + newText);
			} else {
				logger.debug("not match: " + txt);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return newText;
	}

	public static Long sDate2Number(String txt, String pattern) {
		Long ll = new Long(0);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Date date = formatter.parse(txt);
			ll = date.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ll;
	}

	public static Date str2Date(String txt, String pattern) {
		Date dt = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			dt = formatter.parse(txt);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dt;
	}

	protected static Object[] isMatch(Map<String, String> patterns, String txt) throws Exception {
		for (String key : patterns.keySet()) {
			if (isMatch(key, txt)) {
				String format = patterns.get(key);

				// format to same format
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				Date date = formatter.parse(txt);
				String newText = outputFormat.format(date);

				// only return when year is same
				if (txt.substring(0, 4).equals(newText.substring(0, 4))) {
					// return
					Object[] result = new Object[] { true, format };
					return result;
				}

			}
		}
		return new Object[] { false, txt };
	}

	public static String acquireEtlDate(int adv) {
		Calendar cal = Calendar.getInstance();// 使用預設時區和語言環境獲得一個日曆。
		cal.add(Calendar.DAY_OF_MONTH, adv);// 取當前日期的修正.
		String newText = outputFormat.format(cal.getTime());
		return newText;
	}

	protected static boolean isMatch(String pattern, String txt) {
		Matcher m = Pattern.compile(pattern).matcher(txt);
		return m.matches();
	}

	public static boolean LessEqual(Date dIdx, Date dTo) {
		return dIdx.getTime() <= dTo.getTime();
	}

	public static boolean Equ(Date dIdx, Date dTo) {
		return dIdx.getTime() == dTo.getTime();
	}

	public static Date add(Date dIdx, int adv) {
		Calendar cal = Calendar.getInstance();// 使用預設時區和語言環境獲得一個日曆。
		cal.setTimeInMillis(dIdx.getTime());
		cal.add(Calendar.DAY_OF_MONTH, adv);// 取當前日期的修正.
		Date dt = cal.getTime();
		return dt;
	}

	public static String date2str(Date dIdx, String pattern) {
		SimpleDateFormat outputFormat = new SimpleDateFormat(pattern);
		String newText = outputFormat.format(dIdx.getTime());
		return newText;
	}

}
