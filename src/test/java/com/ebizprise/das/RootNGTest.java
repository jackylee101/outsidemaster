package com.ebizprise.das;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RootNGTest extends AbstractTestNGSpringContextTests {

	private Environment environment;

//	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
		Map<String, Object> systemEnvironment = ((ConfigurableEnvironment) environment).getSystemEnvironment();
		System.out.println("=== System Environment ===");
		System.out.println(systemEnvironment);
		System.out.println();

		System.out.println("=== Java System Properties ===");
		Map<String, Object> systemProperties = ((ConfigurableEnvironment) environment).getSystemProperties();
		System.out.println(systemProperties);
	}

	@Test
	public void fool() throws Exception {
		AssertJUnit.assertTrue(true);
	}

	/**
	 * 表單自動化測試 -- 全品項預測 Step1_2: 查出驗證碼
	 */
	protected String pace12(String cookieId) {
		String captchaCode = "";
		return captchaCode;
	}
}
