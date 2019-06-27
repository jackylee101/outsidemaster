package com.ebizprise.das.web.controller.minor;

import java.util.List;

import com.ebizprise.das.pojo.Account;
import com.ebizprise.das.utils.CommonUtils;

public class FPUtil {

	static public String cookieId;
	static public String captchaCode;
	static public String custId;
	static public String prjItmId;
	static public String prjDtlId;
	static public String style;
	static public String filePath;
	static public String email;
	static public String usrpwd;

	static public String realHost;
	public static List<Account> accountList;

	static public String makeUrl(String uri) {
		// String url
		// =forecastParameter.getRealHost()+"http://uatrds.rollingdemand.com.cn"
		// + CommonUtils.PRIFIX
		// + uri;
		String url = realHost + uri;
		return url;
	}
}
