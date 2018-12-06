package com.ebizprise.das;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class RootTest {

	// private Environment environment;
	//
	// @Override
	// public void setEnvironment(Environment environment) {
	// this.environment = environment;
	// Map<String, Object> systemEnvironment = ((ConfigurableEnvironment)
	// environment).getSystemEnvironment();
	// System.out.println("=== System Environment ===");
	// System.out.println(systemEnvironment);
	// System.out.println();
	//
	// System.out.println("=== Java System Properties ===");
	// Map<String, Object> systemProperties = ((ConfigurableEnvironment)
	// environment).getSystemProperties();
	// System.out.println(systemProperties);
	// }

	@Test
	public void fool() throws Exception {
		Assert.assertTrue(true);
	}

}
