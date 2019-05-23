package com.ebizprise.das.druid;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

/*
 * description:
 * @author maduar
 * @date 17/12/2018 3:49 PM
 * @email maduar@163.com
 *
 * */
@Configuration
@Slf4j
public class DataSourceConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.datasource.druid.url:error}")
	private String h2Url;

	@Primary
	@Bean(name = "h2DataSource")
	@ConfigurationProperties("spring.datasource.druid")
	public DataSource tenantDataSource() {
		logger.info("h2Url: " + h2Url);
		return new DruidDataSource();
	}
}
