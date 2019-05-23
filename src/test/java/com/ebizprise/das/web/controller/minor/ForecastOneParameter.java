package com.ebizprise.das.web.controller.minor;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class ForecastOneParameter extends RegisterParameter {

	public String prjDtlId;
	public String prjItmId;

	public List comboParaList;
	public Map<String, Object> firstSku;
	public List<JSONObject> allParaList;
}
