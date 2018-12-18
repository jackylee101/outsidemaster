package com.ebizprise.das;

import java.util.concurrent.Executor;

//import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//@EnableDiscoveryClient
@SpringBootApplication
// @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
// HibernateJpaAutoConfiguration.class })
@EnableScheduling
// @EnableConfigurationProperties(StorageProperties.class)
@EnableAutoConfiguration
// @MapperScan(basePackages = "com.ebizprise.das.db.dao")
@EntityScan(basePackages = "com.ebizprise.das.db.entity")
@EnableJpaRepositories(basePackages = "com.ebizprise.das.db.repository")
// @EnableEurekaServer
// @EnableAsync
public class WebApplication extends SpringBootServletInitializer  {

	private static final Logger logger = LoggerFactory
			.getLogger(WebApplication.class);
	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering main(String[])");
			logger.debug("args: " + args);
		}
		SpringApplication.run(WebApplication.class, args);
		if (logger.isDebugEnabled()) {
			logger.debug("exiting main()");
		}
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("async-");
		executor.initialize();
		return executor;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	// @Bean
	// CommandLineRunner init(StorageService storageService) {
	// if (logger.isDebugEnabled()) {
	// logger.debug("entering init(StorageService)");
	// logger.debug("storageService: " + storageService);
	// logger.debug("exiting init()");
	// logger.debug("returning: " + null);
	// }
	// return (args) -> {
	// storageService.deleteAll();
	// storageService.init();
	// };
	// }

	@Bean
	public LocaleResolver localeResolver() {
		if (logger.isDebugEnabled()) {
			logger.debug("entering localeResolver()");
		}
		SessionLocaleResolver slr = new SessionLocaleResolver();
		// slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		if (logger.isDebugEnabled()) {
			logger.debug("exiting localeResolver()");
			logger.debug("returning: " + slr);
		}
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		if (logger.isDebugEnabled()) {
			logger.debug("entering localeChangeInterceptor()");
		}
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		if (logger.isDebugEnabled()) {
			logger.debug("exiting localeChangeInterceptor()");
			logger.debug("returning: " + lci);
		}
		return lci;
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("entering addInterceptors(InterceptorRegistry)");
//			logger.debug("registry: " + registry);
//		}
//		registry.addInterceptor(localeChangeInterceptor());
//		if (logger.isDebugEnabled()) {
//			logger.debug("exiting addInterceptors()");
//		}
//	}
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("entering addCorsMappings(CorsRegistry)");
//			logger.debug("registry: " + registry);
//		}
//		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
//				.allowCredentials(false).maxAge(3600);
//		if (logger.isDebugEnabled()) {
//			logger.debug("exiting addCorsMappings()");
//		}
//
//	}

	// @Bean
	// public PlatformTransactionManager transactionManager(
	// EntityManagerFactory emf) {
	//
	// logger.warn("資料庫連線: " + jdbc_url);
	// logger.warn("API連線: " + apiUrl);
	//
	// JpaTransactionManager transactionManager = new JpaTransactionManager();
	// transactionManager.setEntityManagerFactory(emf);
	// return transactionManager;
	// }

}
