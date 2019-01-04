package com.ebizprise.das.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	private static final Log LOG = LogFactory.getLog(CommonUtils.class);

	final static public String PRIFIX = "/outsideservant/v1";

	final static public String PRJTABLE = "USER_UPLOAD_DATA";
	
//	web登录相关：
	/** 验证码，Hash类型， 后面跟着cookie Id */
	public static final String CAPTCHA = "captcha:";
	/** 验证码，field，验证码内容*/
	public static final String CAPTCHA_CODE = "code";
	/** 验证码，field，验证码是否已经验证过 */
	public static final String CAPTCHA_CHECKED = "checked";
	/** 验证码失效时间，分钟 */
	public static final int CAPTCHA_EXPIRED = 2;
	

	public boolean isValidDate(String inDate) {
		
		if (inDate == null)
		return false;
	
		//set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
		if (inDate.trim().length() != dateFormat.toPattern().length())
		return false;
	
		dateFormat.setLenient(false);
	
		try {
			//parse the inDate parameter
			dateFormat.parse(inDate.trim());
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String getErrMsg(String errCode) {
//		SysMsgCode code = sysMsgCodeMapper.selectStatusNameByStatusValue(errCode);
//		String errMsg = errCode;
//		if (code!=null) {
//			errMsg = code.getStatusName();
//		}
		String errMsg = "[ErrorCode]";
		
		return errMsg;
	}

	public String getCombineTableName(String custId, String preUnit) {
		String tableName = "";
		if (StringUtils.isBlank(preUnit)) {
			tableName = CommonUtils.PRJTABLE + "_" + custId;
		} else if (StringUtils.equals(preUnit, "D") || StringUtils.equals(preUnit, "W") || 
				StringUtils.equals(preUnit, "M")) {
			tableName = CommonUtils.PRJTABLE + "_" + preUnit + "_" + custId;
		}
		return tableName;
	}
	
}
