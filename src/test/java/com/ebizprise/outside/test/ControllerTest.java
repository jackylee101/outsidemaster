package com.ebizprise.outside.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ebizprise.das.RootTest;
import com.ebizprise.das.TestApplicationConfig;
import com.ebizprise.das.utilsweb.form.jwt.TokenKeyForm;
import com.ebizprise.das.web.controller.v1.WebController;
import com.ebizprise.outside.base.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

/*
 *
 * @author maduar
 * @date 31/07/2018 2:19 PM
 * @email maduar@163.com
 *
 * */

@RunWith(SpringRunner.class)
// @SpringBootTest(classes = { WebApplication.class })
@SpringBootTest(classes = TestApplicationConfig.class, webEnvironment = WebEnvironment.DEFINED_PORT)
// @ActiveProfiles("test")
// @TestPropertySource(locations = "classpath:application.yml")
// @ContextConfiguration(initializers =
// ConfigFileApplicationContextInitializer.class, classes = {
// TestApplicationConfig.class })
public class ControllerTest extends RootTest {
	private static final Logger logger = LoggerFactory
			.getLogger(ControllerTest.class);

	@Autowired
	private TestRestTemplate restTemplate;// = new TestRestTemplate();

	@Autowired
	private WebController webController;

	TestUtils testUtils = new TestUtils();

	@Value("${server.context-path}")
	private String uri;

	// @Test
	public void contexLoads() throws Exception {
		assertThat(webController).isNotNull();
	}

	// TestUtils testUtils = new TestUtils();
	// @Test
	public void getTest() {
		String body = restTemplate.getForObject("/test2/1", String.class);
		System.out.println("body: " + body);
		Assert.assertEquals("code: TITLE 字段数与DATA字段数不符", body);
		System.out.println("aa");
	}

	// @Test
	public void postTest() {

		TokenKeyForm t2 = new TokenKeyForm("000040", "00xx00");
		String url = uri + "/test";

		HttpEntity<String> httpEntity = testUtils.createFormEntity(t2);
		TokenKeyForm tokenKeyForm = restTemplate.postForObject(url, httpEntity,
				TokenKeyForm.class);

		Assert.assertEquals(t2.getCustId(), tokenKeyForm.getCustId());
	}

	/**
	 * 請注意@RequestBody註解 添加@ModelAttribute，表示是預設form型態
	 * Form解析可以直接从Request对象中获取请求参数
	 * ，这样对象转换与处理相对容易，但在大片JSON数据需要提交时，可能会出现大量的数据拆分与处理工作，另外针对集合类型的处理，也是其比较孱弱的地方。
	 * 
	 * @param account
	 * @return
	 */
	@Test
	public void formPostTest1() throws Exception {
		String url = "/formPostTest1";
		HttpHeaders headers = new HttpHeaders();
		// 請勿輕易改變此提交方式，大部分的情況下，提交方式都是表單提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// 封裝參數，千萬不要替換為Map與HashMap，否則參數無法傳遞
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// 也支持中文
		params.add("username", "用户名");
		params.add("password", "123456");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				params, headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, requestEntity, String.class);
		// 輸出結果
		logger.info(response.getBody());
		assertThat(response.getBody()).isEqualTo(
				"{\"success\":true,\"error\":null}");
	}

	/**
	 * 請注意@RequestBody註解 千萬不要畫蛇添足添加@ModelAttribute，否則會被其覆蓋，如下
	 * 而Payload的优势是一次可以提交大量JSON字符串
	 * ，但无法从Request从获取参数，也会受限于JSON解析的深度（尤其是有多层对象级联的情况，最底层的对象几乎无法转换为具体类型）。
	 * 
	 * @param account
	 * @return
	 */
	@Test
	public void formPostTest21() throws Exception {
		String url = "/formPostTest2";
		formPostTest2(url);
	}

	@Test
	public void formPostTest22() throws Exception {
		String url = "/formPostTest3";
		formPostTest2(url);
	}

	private void formPostTest2(String url) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		// 請勿輕易改變此提交方式，大部分的情況下，提交方式都是表單提交
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		// 將提交的數據轉換為String
		// 最好通過bean注入的方式獲取ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> params = Maps.newHashMap();
		params.put("username", "國米");
		params.put("password", "123456");
		String value = mapper.writeValueAsString(params);
		HttpEntity<String> requestEntity = new HttpEntity<String>(value,
				headers);
		// 執行HTTP請求
		ResponseEntity<String> response = restTemplate.postForEntity(url,
				requestEntity, String.class);
		// 輸出結果
		logger.info(response.getBody());
		assertThat(response.getBody()).isEqualTo(
				"{\"success\":true,\"error\":null}");
	}

	@Test
	public void fileUpload() throws Exception {
		String url = "/fileUpload";
		String filePath = "C:\\Users\\jacky.lee\\Documents\\Default.rdp";

		FileSystemResource resource = new FileSystemResource(new File(filePath));
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("jarFile", resource);
		param.add("fileName", "test.txt");

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				param);
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, requestEntity, String.class);
		logger.info(response.getBody());
		assertThat(response.getBody()).isEqualTo(
				"{\"success\":true,\"error\":null}");

		// String string = restTemplate.postForObject(url, param, String.class);
		// logger.info(string);

	}

	// @Test
	public void testAccount() throws Exception {
		formPostTest1();
		// formPostTest1();
	}

}
