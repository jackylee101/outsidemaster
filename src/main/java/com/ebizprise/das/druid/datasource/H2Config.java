package com.ebizprise.das.druid.datasource;

//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.ebizprise.das.db.entity")
@EnableJpaRepositories(basePackages = "com.ebizprise.das.db.repository")
// @MapperScan(basePackages = "com.ebizprise.das.auth.dao", sqlSessionFactoryRef
// = "h2SqlSessionFactory")
public class H2Config {

	@Resource
	@Qualifier("h2DataSource")
	private DataSource h2DataSource;

	// @Primary
	// @Bean(name = "h2TransactionManager")
	// public DataSourceTransactionManager h2TransactionManager(
	// @Qualifier("h2DataSource") DataSource h2DataSource) {
	// return new DataSourceTransactionManager(h2DataSource);
	// }

	// @Bean(name = "h2SqlSessionFactory")
	// public SqlSessionFactory h2SqlSessionFactory(@Qualifier("h2DataSource")
	// DataSource h2DataSource) throws Exception {
	// final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	// sessionFactory.setDataSource(h2DataSource);
	// return sessionFactory.getObject();
	// }
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("h2DataSource") DataSource dataSource)
			throws IOException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "com.ebizprise.das.db.entity" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		em.setJpaProperties(additionalProperties());
		return em;
	}

	private Properties additionalProperties() {
		final Properties jpaProperties = new Properties(); // java.util
		jpaProperties.setProperty("hibernate.show_sql", "true");
		jpaProperties.setProperty("hibernate.ddl-auto", "update");
		jpaProperties.setProperty("hibernate.dialect",
				"com.ebizprise.das.utils.EbizSQLServerDialect");
		return jpaProperties;
	}

//	@Bean
//	public PlatformTransactionManager transactionManager(
//			EntityManagerFactory emf) {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(emf);
//		return transactionManager;
//	}
}
