package com.ebizprise.das.web.controller.v1;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ebizprise.das.WebApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
// @ContextConfiguration(initializers =
// ConfigFileApplicationContextInitializer.class, classes = {
// TestApplicationConfig.class })
public class PaceBase {

	private static final Log logger = LogFactory.getLog(PaceBase.class);

	@Autowired
	protected TestRestTemplate restTemplate;

	// @Test
	// public void fool() throws Exception {
	// Assert.assertTrue(true);
	// }

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

}