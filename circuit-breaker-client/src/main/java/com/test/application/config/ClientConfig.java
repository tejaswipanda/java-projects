package com.test.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

	@Value("${test.demo}")
	private String testDemo;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	String printValues() {
		System.out.println(testDemo);// Value from config server
		return testDemo;
	}

}
