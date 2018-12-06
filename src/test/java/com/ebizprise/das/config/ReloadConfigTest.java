package com.ebizprise.das.config;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import com.ebizprise.das.RootTest;

public class ReloadConfigTest extends RootTest {
	private static final Logger logger = LoggerFactory
			.getLogger(ReloadConfigTest.class);

	@Autowired
	ReloadConfig reloadConfig;

	@Autowired
	ConfigurableEnvironment env;
	// @Autowired
	// ArchaiusAutoConfiguration archaiusAutoConfiguration;

	// private DynamicStringProperty archaiusTest = DynamicPropertyFactory
	// .getInstance().getStringProperty("baseUrl.syncUrl1", "test1");
	//
	// static Map<String, Long> lastModifiedMap = new HashMap();

	@Before
	public void setUp() throws Exception {
		// // 设置回调
		// archaiusTest.addCallback(() -> {
		// logger.warn("設定變了  new value: " + archaiusTest.get());
		// });
	}

//	@Test
	public void contextLoads() throws Exception {
		reloadConfig.checkAllTime();
		while (true) {
			Thread.sleep(6000);
			System.out.println("env config: "
					+ env.getProperty("spring.profiles.active"));
			System.out.println("main config: "
					+ env.getProperty("server.context-path"));
			System.out.println("env config: "
					+ env.getProperty("baseUrl.syncUrl1"));
//			System.out.println("archaius config: " + archaiusTest.get());
		}
	}

	// public void init() {
	//
	// ScheduledExecutorService scheduled = Executors
	// .newScheduledThreadPool(1);
	// scheduled.scheduleAtFixedRate(new Runnable() {
	// @Override
	// public void run() {
	// reloadConfig.checkConfigModified();
	// }
	//
	// }, 1, 10, TimeUnit.SECONDS); // 测试时间比较短，根据实际需求修改，如5分钟
	// }

}