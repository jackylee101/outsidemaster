package com.ebizprise.das.web.controller.minor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ebizprise.das.form.system.QueryForm;
import com.ebizprise.das.web.controller.minor.FPUtil;
import com.ebizprise.das.web.controller.minor.PaceBase;
import com.ebizprise.das.web.controller.minor.PaceStyle;

@Component
public class PaceStyle8 extends PaceBase implements PaceStyle {

	private static final Log logger = LogFactory.getLog(PaceStyle1.class);

	public PaceStyle8() {
		super();
	}

	@Override
	public String getStyles() {
		return "RDS_SampleData";
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step3:
	 */
	public QueryForm pace3(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep3/");

		List obja = new ArrayList();
		obja.add("col004");
		obja.add("col005");
		obja.add("col006");
		obja.add("col007");
		

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("sltCol", obja);

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step4:
	 */
	public QueryForm pace4(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep4/");

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("setDate", "COL006");

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step5:
	 */
	public QueryForm pace5(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep5/");

		List obja1 = new ArrayList();
		obja1.add("COL004");
		obja1.add("COL005");

		List obja2 = new ArrayList();
		obja2.add("COL007");

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("setTxt", obja1);
		params.put("setNum", obja2);

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step6:
	 */
	public QueryForm pace6(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep6/");

		List obja1 = new ArrayList();

		Map obj1 = new HashMap();
		obj1.put("key", 0);
		obja1.add(obj1);

		Map obj2 = new HashMap();
		obj2.put("key", 0);
		obja1.add(obj2);

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("relSet", obja1);

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step7:
	 */
	public QueryForm pace7(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep7/");

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("returnType", "02");
		params.put("numMissing", "01");

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step8:
	 */
	public QueryForm pace8(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep8/");

		List obja1 = new ArrayList();
		List obja2 = new ArrayList();
		List obja3 = new ArrayList();
		List obja4 = new ArrayList();
		obja3.add("COL004");
		obja4.add("COL005");

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("level1", obja1);
		params.put("level2", obja2);
		params.put("level3", obja3);
		params.put("level4", obja4);

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step9:
	 */
	public QueryForm pace9(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep9/");

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("nq", "COL007");

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step10:
	 */
	public QueryForm pace10(String custId, String prjItmId, String prjDtlId) {
		String url = FPUtil.makeUrl("/prjImportStep10/");

		List key1 = new ArrayList();
		List key2 = new ArrayList();
		List key3 = new ArrayList();
		List key4 = new ArrayList();

		Map key1o = new HashMap();
		key1o.put("key", "01");
		key1o.put("sourceCol", "COL004");
		key1.add(key1o);

		Map key2o = new HashMap();
		key2o.put("key", "02");
		key2o.put("sourceCol", "COL005");
		key2.add(key2o);

		List sortKey1 = new ArrayList();
		List sortKey2 = new ArrayList();
		List sortKey3 = new ArrayList();
		List sortKey4 = new ArrayList();

		Map sortKey1o = new HashMap();
		sortKey1.add(sortKey1o);

		Map sortKey2o = new HashMap();
		sortKey2.add(sortKey2o);

		Map sortKey3o = new HashMap();
		sortKey3o.put("sourceCol", "COL004");
		sortKey3.add(sortKey3o);

		Map sortKey4o = new HashMap();
		sortKey4o.put("sourceCol", "COL005");
		sortKey4.add(sortKey4o);

		Map<String, Object> params = new HashMap();
		params.put("custId", custId);
		params.put("prjItmId", prjItmId);
		params.put("prjDtlId", prjDtlId);
		params.put("key1", key1);
		params.put("key2", key2);
		params.put("key3", key3);
		params.put("key4", key4);
		params.put("sortKey1", sortKey1);
		params.put("sortKey2", sortKey2);
		params.put("sortKey3", sortKey3);
		params.put("sortKey4", sortKey4);
		params.put("preUnit", "D");

		ResponseEntity<String> response = paceType(url, HttpMethod.PUT, params);
		// 輸出結果
		logger.info(response.getBody());
		QueryForm queryForm = JSON.parseObject(response.getBody(),
				QueryForm.class);

		return queryForm;
	}

}
