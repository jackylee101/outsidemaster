package com.ebizprise.das.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//import com.ebizprise.das.utilsweb.apiVersion.ApiVersionRequestMappingHandlerMapping;
//import com.ebizprise.das.utilsweb.interceptor.AuthInterceptor;
//import com.ebizprise.das.utilsweb.interceptor.JWTInterceptor;

/*
 *
 * @author maduar
 * @date 13/08/2018 2:08 PM
 * @email maduar@163.com
 *
 * */
//@SpringBootConfiguration
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	// @Bean
	// public AuthInterceptor interceptor(){
	// return new AuthInterceptor();
	// }
	//
	// @Bean
	// public JWTInterceptor jWTInterceptor() {
	// return new JWTInterceptor();
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(jWTInterceptor()).addPathPatterns("/**");
		// //对来自/** 全路径请求进行拦截
		// registry.addInterceptor(interceptor());
	}

	// @Override
	// @Bean
	// public RequestMappingHandlerMapping requestMappingHandlerMapping() {
	// RequestMappingHandlerMapping handlerMapping = new
	// ApiVersionRequestMappingHandlerMapping();
	// handlerMapping.setOrder(0);
	// handlerMapping.setInterceptors(getInterceptors());
	// return handlerMapping;
	// }

	// @LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
