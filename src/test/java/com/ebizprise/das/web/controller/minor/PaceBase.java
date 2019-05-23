package com.ebizprise.das.web.controller.minor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ebizprise.das.form.system.QueryForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaceBase {

	private static final Log logger = LogFactory.getLog(PaceBase.class);

	@Autowired
	protected TestRestTemplate restTemplate;

//	@Autowired
//	protected DasFcstProcessStatusMapper dasFcstProcessStatusMapper;
//
//	@Autowired
//	protected UserProdCatgMapper userProdCatgMapper;

	// @Test
	// public void fool() throws Exception {
	// Assert.assertTrue(true);
	// }

	protected ResponseEntity<String> paceType(String url, HttpMethod method, Map<String, ?> params) {
		HttpHeaders headers = new HttpHeaders();
		// 請勿輕易改變此提交方式，大部分的情況下，提交方式都是表單提交
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		// 封裝參數，千萬不要替換為Map與HashMap，否則參數無法傳遞
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(params);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(value, headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
	}

	protected ResponseEntity<String> paceType(String token, String url, HttpMethod method, Object param) {
		HttpHeaders headers = new HttpHeaders();
		// 請勿輕易改變此提交方式，大部分的情況下，提交方式都是表單提交
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("Authorization", token);
		// 封裝參數，千萬不要替換為Map與HashMap，否則參數無法傳遞
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(value, headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
	}
//	protected boolean allOK(String custId, String prjItmId, String prjDtlId) {
//		List<String> list = dasFcstProcessStatusMapper
//				.findBatchidListBy3(prjItmId);
//
//		if (list.size() == 3 )//&& list2.size() > 0)
//			return true;
//		else
//			return false;
//	}

	public QueryForm pace11(String custId, String prjItmId, String prjDtlId) {
		QueryForm queryForm = new QueryForm();
//		queryForm.setSuccess(false);
//		for (int i = 0; i < 100000; i++) {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if (allOK(custId, prjItmId, prjDtlId)) {
//				queryForm.setSuccess(true);
//				queryForm.setQueryString(String.valueOf(i));
//				break;
//			}
//		}
		return queryForm;
	}
}