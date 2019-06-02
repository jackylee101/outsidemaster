package com.ebizprise.das.scheduled.service.weather.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebizprise.das.form.base.DvdForm2;
import com.ebizprise.das.form.base.PriceForm2;
import com.ebizprise.das.utils.DateUtil;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
@Service
public abstract class CommonService {
	private static final Log logger = LogFactory.getLog(CommonService.class);

	protected DvdForm2 queryDvd(List list, Date dIdx) {
		DvdForm2 keep = new DvdForm2();
		for (int i = 0; i < list.size(); i++) {
			JSONObject dvd = (JSONObject) list.get(i);
			DvdForm2 dvdForm2 = JSON.parseObject(dvd.toJSONString(),
					DvdForm2.class);
			String localDate = dvdForm2.getExDate();
			Date dlocalDate = DateUtil.str2Date(localDate, "yyyyMMdd");
			if (DateUtil.Less(dlocalDate, dIdx))
				keep = new DvdForm2(dvdForm2);
			if (DateUtil.Equ(dlocalDate, dIdx))
				return dvdForm2;
		}
		if (keep != null)
			keep.setExDate(DateUtil.date2str(dIdx, "yyyyMMdd"));
		return keep;
	}

	protected PriceForm2 queryPrice(List list, Date dIdx) {
		PriceForm2 keep = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject price = (JSONObject) list.get(i);
			PriceForm2 priceForm2 = JSON.parseObject(price.toJSONString(),
					PriceForm2.class);
			String localDate = priceForm2.getLocalDate();
			Date dlocalDate = DateUtil.str2Date(localDate, "yyyyMMdd");
			if (DateUtil.Less(dlocalDate, dIdx))
				keep = new PriceForm2(priceForm2);
			if (DateUtil.Equ(dlocalDate, dIdx))
				return priceForm2;
		}
		String targetDate = DateUtil.date2str(dIdx, "yyyyMMdd");
		if (keep == null) {
			logger.warn(targetDate + " 沒資料 還沒開始有資料");
			return keep;
		}
		logger.warn(targetDate + " 沒資料,用 " + keep.getLocalDate() + " 代替");
		keep.setLocalDate(targetDate);
		return keep;
	}

}
