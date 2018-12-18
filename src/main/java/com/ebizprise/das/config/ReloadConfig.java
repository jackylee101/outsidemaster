package com.ebizprise.das.config;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

@Configuration
public class ReloadConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(ReloadConfig.class);

	@Autowired
	ConfigurableEnvironment env;

	private DynamicStringProperty archaiusTest = DynamicPropertyFactory
			.getInstance().getStringProperty("baseUrl.syncUrl1", "test1");

	static Map<String, Long> lastModifiedMap = new HashMap();

	static String mainConfigPath;
	static String subConfigPath;

	// public void contextLoads() throws Exception {
	// while (true) {
	// Thread.sleep(6000);
	// // System.out.println("env config: "
	// // + env.getProperty("spring.profiles.active"));
	// // System.out.println("main config: "
	// // + env.getProperty("server.context-path"));
	// // System.out.println("env config: "
	// // + env.getProperty("baseUrl.syncUrl1"));
	// // System.out.println("archaius config: " + archaiusTest.get());
	// }
	// }

	@Bean
	public ScheduledExecutorService checkAllTime() {
		// 设置回调
		archaiusTest.addCallback(() -> {
			logger.warn("設定變了  new value: " + archaiusTest.get());
		});
		// 確認設定檔是外部還是內部
		findConfigPath();

		ScheduledExecutorService scheduled = Executors
				.newScheduledThreadPool(1);
		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				checkConfigModified();
			}

		}, 1, 10, TimeUnit.SECONDS); // 测试时间比较短，根据实际需求修改，如5分钟
		return scheduled;
	}

	private void findConfigPath() {
		String profile = env.getProperty("spring.profiles.active");

		String absolutePath = new File("").getAbsolutePath(); // 获取jar包当前的目录
		mainConfigPath = absolutePath + "/application.yml"; //
		mainConfigPath = mainConfigPath.replace("\\", "/");// 拼好文件路径
		File f = new File(mainConfigPath);
		if (f.exists()) {// 外部設定檔存在
			logger.info("採用外部設定檔: " + mainConfigPath);
			subConfigPath = absolutePath + "/application-" + profile + ".yml";
			File f1 = new File(subConfigPath);
			if (f1.exists()) {// 外部子設定檔不存在
				logger.info("採用外部設定檔: " + subConfigPath);
			} else {
				subConfigPath = null;
			}
		} else {// 內部設定檔必須存在
			URI uri1 = null;
			URI uri2 = null;
			try {
				uri1 = this.getClass().getResource("/application.yml").toURI();
				mainConfigPath = uri1.getPath();
				logger.info("採用內部設定檔: " + mainConfigPath);

				subConfigPath = "/application.yml";
				if (profile != null)
					subConfigPath = "/application-" + profile + ".yml";
				uri2 = this.getClass().getResource(subConfigPath).toURI();
				subConfigPath = uri2.getPath();
				File f1 = new File(subConfigPath);
				if (f1.exists()) {// 內部子設定檔不存在
					logger.info("採用內部設定檔: " + subConfigPath);
				} else {
					subConfigPath = null;
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	void checkConfigModified() {
		if (checkConfigModified(mainConfigPath, lastModifiedMap)) {
			changeConfig(mainConfigPath);
			logger.warn("設定檔: " + mainConfigPath + " 有異動");
		}

		if (subConfigPath == null)
			return;

		if (checkConfigModified(subConfigPath, lastModifiedMap)) {
			changeConfig(subConfigPath);
			logger.warn("設定檔: " + subConfigPath + " 有異動");
		}
	}

	private void changeConfig(String configFileName) {
		try {
			FileSystemResource fileSystemResource = new FileSystemResource(
					configFileName);

			YamlPropertySourceLoader yamLoader = new YamlPropertySourceLoader();

			List<PropertySource<?>> yamPropList = yamLoader.load("YamFileName",
					fileSystemResource);
			
			MutablePropertySources sources = env.getPropertySources();
			
			for (int i = 0; i < yamPropList.size(); i++) {
				PropertySource<LinkedHashMap> yamProp = (PropertySource<LinkedHashMap>) yamPropList
						.get(i);
				sources.addFirst(yamProp);
				
				LinkedHashMap linkedHashMap = yamProp.getSource();
				logger.warn(configFileName + " props size: " + linkedHashMap.size());
				
			}
//			PropertySource<LinkedHashMap> yamProp = (PropertySource<LinkedHashMap>) yamLoader
//					.load("YamFileName", fileSystemResource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkConfigModified(String configFileName,
			Map<String, Long> lastModifiedMap) {

		Long lastModified = lastModifiedMap.get(configFileName);

		FileSystemResource fileSystemResource = new FileSystemResource(
				configFileName);
		Long lastModified1 = fileSystemResource.getFile().lastModified();
		if (lastModified1.equals(lastModified))
			return false;

		lastModifiedMap.put(configFileName, lastModified1);
		return true;

	}
}