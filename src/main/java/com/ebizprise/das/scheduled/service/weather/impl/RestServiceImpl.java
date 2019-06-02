package com.ebizprise.das.scheduled.service.weather.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ebizprise.das.scheduled.service.weather.RestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
@Service
public class RestServiceImpl extends CommonService implements RestService {
	private static final Log logger = LogFactory.getLog(RestServiceImpl.class);

	@Autowired
	protected RestTemplate restTemplate;

	protected ResponseEntity<String> paceType(String url, HttpMethod method,
			Map<String, ?> params) {
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
		HttpEntity<String> requestEntity = new HttpEntity<String>(value,
				headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.exchange(url, method,
				requestEntity, String.class);
		return response;
	}

	protected ResponseEntity<String> paceType(String token, String url,
			HttpMethod method, Object param) {
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
		HttpEntity<String> requestEntity = new HttpEntity<String>(value,
				headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.exchange(url, method,
				requestEntity, String.class);
		return response;
	}

}
